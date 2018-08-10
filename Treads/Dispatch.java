package Treads;

import Model.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Dispatch implements Runnable {
    private List <ImageView> listManView ;
    private List<ImageView> listCarView ;
    private  List<CallLog> callLogList = new LinkedList<>();
    public static volatile boolean semaphoreCall = true ;

    private int pointStart;
    private int loadingPlace ;
    private int unloadingPlace ;
    private WayPoint [] waypointProcessedArry = new WayPoint[City.STREET.length];// обработанные точки пути
    // массив в котором сохраняются данные о местоположении автомобиля 0 ячейка исходная точка пути.
    private int [] routStart = {0,0,0};

    private ImageView currentPassenger ;


    public Dispatch(List<ImageView> listManView, List<ImageView> listCarView) {
        this.listManView = listManView;
        this.listCarView = listCarView;
    }

    @Override
    public void run() {
         //запускаем поток генератор звонков
        CallLog callLog = new CallLog();
        DriveLog driveLog =new DriveLog();
        Thread threadGeneratorCall = new Thread(new GeneratorPassenger(callLog));
        threadGeneratorCall.start();

        // запускаем потоки  автомобилей
        for (int i = 0 ; i < listCarView.size(); i++) {
            Thread threadCar = new Thread( new driveCar(6+i,listCarView.get(i),driveLog,i));
            threadCar.start();
        }
        // расставляем пассажиров и делаем их невидимыми
        ImageView viewMan ;
        for (int i = 0 ; i < listManView.size(); i++) {
            viewMan = listManView.get(i);
            viewMan.setVisible(false);
            viewMan.setLayoutX(City.INFRASTRUCTURE [i+1][0]);
            viewMan.setLayoutY(City.INFRASTRUCTURE [i+1][1]);
        }

        while (!Thread.currentThread().isInterrupted()) {
            // если есть вызовов то помещаем его в журнал, высвечиваем на карте пассажира и ставим статус "свободно",
            if(!semaphoreCall) {

                callLogList.add(new CallLog(callLog.getLoadingPlace(),callLog.getUnloadingPlace()));
                // показываем на карте пассажира и запоминаем его
                currentPassenger = listManView.get(callLog.getLoadingPlace()-1);
                currentPassenger.setVisible(true);
                System.out.println(" callLogList.add - " + callLog.getLoadingPlace() +" -   " +
                        callLog.getUnloadingPlace());
                //разрешаем писать в журнал следующую запись
                semaphoreCall = true;
            }
            if (callLogList.size()>0){
                // смотрим есть ли свободные машины и у свободных машин выясняем кратчайший путь.
                ShortRoute [] arrayShortRouteLoadTemp = new ShortRoute [3];
                int distanceMin=1000000000;
                int index = -1;
                for (int i=0 ; i<3 ;i++){
                    if (driveLog.getFreeCar()[i]){
                        arrayShortRouteLoadTemp[i]= routeSearch (routStart [i] , City.TAXIstops[callLogList.get(0).getLoadingPlace()]);
                        if (index == -1 ) {
                            distanceMin = arrayShortRouteLoadTemp[i].getDistance();
                            index = i;
                        }
                        else if (distanceMin > arrayShortRouteLoadTemp[i].getDistance()){
                            distanceMin = arrayShortRouteLoadTemp[i].getDistance();
                            index = i ;
                        }
                    }
                }
                // если свободные машины есть добавляем в журнал новый маршрут

                if (index >=0 && index < 3){
                    pointStart = routStart[index];
                    loadingPlace =City.TAXIstops[callLogList.get(0).getLoadingPlace()];
                    unloadingPlace = City.TAXIstops[callLogList.get(0).getUnloadingPlace()];
                    routStart[index]= unloadingPlace ;
                    System.out.println("Dispach start work poin "+ pointStart +" - " +
                            loadingPlace +" - "+ unloadingPlace  );
                    driveLog.setPathStart(routeSearch(pointStart,loadingPlace).shortPach,index);
                    driveLog.setPathUnload(routeSearch(loadingPlace,unloadingPlace).shortPach,index);
                    driveLog.setDistance(routeSearch(loadingPlace,unloadingPlace).getDistance(),index);

                    //добавляем в журнал пассажира которого нужно перевезти и вид пассажира на выгрузке
                    driveLog.setViewManLoad(currentPassenger,index);
                    driveLog.setViewManUnload(listManView.get(callLog.getUnloadingPlace()-1),index);
                    //добавляем точки погрузки и выгрузки пассажиров
                    driveLog.setPozStart(callLog.getLoadingPlace(),index);
                    driveLog.setPozStart(callLog.getUnloadingPlace(),index);

                    //ставим флаг что машина уже занята
                    driveLog.setCellFreeCarFalse(index);
                    // ставим флаг что у машины есть задание (после єтого поток начнет движение по маршруту
                    driveLog.setCellTaskTrue(index);

                    // удаляем первую запись с журнала,вызов отработан
                    callLogList.remove(0);
                    System.out.println("Size callLogList - "+ callLogList.size()) ;
                }


            }

        }

    }


    // метод ищет кратчайшие пути ко всем доступным точкам на карте
    // и возвращает кратчайший маршрут Path от начальной до конечной точки
    // метод может не работать если две соседние точки ссылаются друг на друга.

    private ShortRoute routeSearch (int startRoute , int endRoute ){
       if (startRoute!=endRoute ){
           List <WayPoint> listWaypoint = new ArrayList<>();  // не обрабатанные точки пути
           List <Integer> listShortRoute = new ArrayList<>(); // кратчайший маршрут
           List <Integer> listShortRoutePrevious = null ;

           // обнуляев массив waypointProcessedArry
           for (int i=0;i < waypointProcessedArry.length; i++) {
               waypointProcessedArry[i]= null;
           }

           int tempPoint ;
           int nextPoint ;
           int distance ;
           int distanceMin = 1000000000 ;
           // добавляем точку старта в список точек обработки и с нее начинаем поиск
           tempPoint = startRoute ;
           listWaypoint.add (new WayPoint (tempPoint ,0,tempPoint ))  ;
           do {
               // используем промежуточный массив для необработанных точек
               List <WayPoint> listWaypointTemp = new ArrayList<>(listWaypoint);
               listWaypoint.clear();

               // у необработанных точек берем все ссылки на все следующие точк
               for (WayPoint wayPointTemp : listWaypointTemp){
                   for (int i = 2; i < City.STREET[wayPointTemp.getNumber()].length ; i++ ){
                       tempPoint = wayPointTemp.getNumber() ;
                       nextPoint = City.STREET[tempPoint][i] ;
                       distance = City.distancePoint(tempPoint,nextPoint);
                       distance += wayPointTemp.getDistance();
                       //boolean addPoint = true ;
                       // проверяем есть ли данная точка в списке обработанных,
                       // если есть то проверяем какой путь короче и если нужно меняем параметры на более короткий путь
                       if (waypointProcessedArry[nextPoint] == null ){
                           if (nextPoint == endRoute ){
                               waypointProcessedArry[endRoute] = new WayPoint(nextPoint,distance,tempPoint) ;

                           }
                           else {
                               listWaypoint.add (new WayPoint (nextPoint,distance,tempPoint ));
                           }
                       }else if (distance < waypointProcessedArry[nextPoint].getDistance()){
                           if (nextPoint == endRoute ){
                               waypointProcessedArry[nextPoint].setDistance(distance);
                               waypointProcessedArry[nextPoint].setPrevious(tempPoint);
                           }else
                           waypointProcessedArry[nextPoint].setDistance(distance);
                           waypointProcessedArry[nextPoint].setPrevious(tempPoint);
                           waypointProcessedArry[nextPoint].setNumber(nextPoint);
                           listWaypoint.add (waypointProcessedArry[nextPoint]);
                       }
                   }
                   // записываем в массив обработанную точку пути (прошлись по всех ее ссылках)
                   waypointProcessedArry[tempPoint] = wayPointTemp ;
                   for (int i = 2; i < City.STREET[wayPointTemp.getNumber()].length ; i++ ){
                       nextPoint = City.STREET[tempPoint][i] ;
                       if (nextPoint == endRoute && distanceMin > waypointProcessedArry[endRoute].getDistance()){
                           distanceMin = waypointProcessedArry[endRoute].getDistance();
                           listShortRoute.clear();
                           listShortRoute.add (endRoute);
                           // в цикле идем от конечной точки и по ссылке previous добавляем в предыдущий элемент в listShortRoute
                           // пока не дойдем до точки старта
                           // если размер листа будет больше 57 это аварийная  ситуация
                           while (listShortRoute.get(listShortRoute.size()-1) != startRoute && listShortRoute.size()<= 57 ){
                               WayPoint wayPoint = waypointProcessedArry[listShortRoute.get(listShortRoute.size()-1)];
                               listShortRoute.add(wayPoint.getPrevious());
                           }
                           if (listShortRoute.size()< 57 ){
                               listShortRoutePrevious = listShortRoute ;
                           }else if (listShortRoutePrevious != null){
                               listShortRoute = listShortRoutePrevious ;
                           }

                       }

                   }

               }
           } while (listWaypoint.size() > 0);


           //для отладки
           if (listShortRoute.size()>= 57){
               int i=0;
               for (var wayPoint:waypointProcessedArry) {
                   System.out.println("№ " + i + " - "+ wayPoint);
               }
           }
           // создаем Path кратчайшего пути
           Path shortPath = new Path();
           // стартуем с заданной    точки
           MoveTo moveTo = new MoveTo();
           moveTo.setX(City.STREET[startRoute][0]);
           moveTo.setY(City.STREET[startRoute][1]);
           shortPath.getElements().add(moveTo);
           //записываем в путь линии кратчайшего пути
           for (int i =  listShortRoute.size()-1; i>=0 ; i-- ){
               shortPath.getElements().add(new LineTo(City.STREET[listShortRoute.get(i)][0] ,City.STREET[listShortRoute.get(i)][1]));
           }
           return new ShortRoute(shortPath,distanceMin) ;
       }
        return new ShortRoute(null,0) ;
    }
}
