package Treads;

import Model.CallLog;

import java.util.Random;

import static Treads.Dispatch.semaphoreCall;


public class GeneratorPassenger implements Runnable{

    private int expectation ;
    private CallLog callLog ;
    private int loadingPlace ;
    private int unloadingPlace = 0;

    public GeneratorPassenger(CallLog callLog ) {
        this.callLog = callLog;
    }

    @Override
    public void run() {
        Random random = new Random();
        int loadingPlacePrevious = 0 ;
        int unloadingPlacePrevious = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (semaphoreCall) {
                do {
                    loadingPlace = 1 + random.nextInt(11);
                    //System.out.println("random loadingPlace - " + loadingPlace );
                }while (loadingPlace == loadingPlacePrevious );
                 // if (loadingPlace == 7)  loadingPlace = 8 ;//при параметре 7 наибольшее число сбоев
                loadingPlacePrevious = loadingPlace ;

                while (loadingPlace == unloadingPlace || unloadingPlace == 0 || unloadingPlace == unloadingPlacePrevious ) {
                    unloadingPlace = 1 + random.nextInt(11);
                }
                unloadingPlacePrevious = unloadingPlace ;
                //System.out.println("random unloadingPlace - " + unloadingPlace );
                callLog.setLoadingPlace(loadingPlace) ;
                callLog.setUnloadingPlace(unloadingPlace) ;
                expectation = 2500 + random.nextInt(5000);
                semaphoreCall = false ;
                try {
                    Thread.sleep(expectation);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
