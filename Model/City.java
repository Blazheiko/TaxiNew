package Model;

public final class City {

    // ������ Street �������� ��������� �������� �����
    // ������ (������ 0) �������� ���������� �
    // ������ (������ 1) �������� ���������� Y
    // ��������� �������� ��� ������ �������� ����� � �������� ���� ����� (�������� ���������)
    public static final int [][] STREET = {
            {0,0,1},      // �0������� ���������� ������� �����
            {105,45,49,6}, //1
            {415,45,1,7},  //2
            {655,45,2,9},   //3
            {910,45,3,11},   // 4
            {1150,45,4,55}, // �5

            {135,75,7,54},   //�6
            {385,75,8,15},  // 7
            {415,75,9,2},   //8
            {625,75,10,16}, //9
            {655,75,11,3},  //10
            {885,75,12,18}, //11
            {910,75,13,4},  //12
            {1120,75,20}, //13

            {135,230,6},  //14
            {385,230,22}, //15
            {625,230,24}, //16
            {655,230,10}, //17
            {885,230,26}, //18
            {885,230,26}, //18
            {1120,230,28}, //20
            {1150,230,5},  //21

            {385,375,36,29}, //22
            {415,375,8,22},  //23
            {625,375,30,23}, //24
            {655,375,24,17}, //25
            {885,375,32,25},  //26
            {910,375,26,12},  //27
            {1120,375,34,27},  //28

            {415,405,23,30},  //29
            {625,405,37,31},  //30
            {655,405,25,32},   //31
            {885,405,46,33},   //32
            {910,405,27,34},   //33
            {1120,405,48,35},  // 34
            {1150,405,21,28},  //35

            {385,540,42},      //36
            {625,540,44},      //37
            {655,540,31},      //38
            {910,540,33},      //39
            {1150,540,35},     //40

            {135,700,14},      //41
            {385,700,41,50},   //42
            {415,700,42,29},   //43
            {625,700,43,51},   //44
            {655,700,44,38},   //45
            {885,700,45,52},   //46
            {910,700,46,39},   //47
            {1120,700,47,56},  //48

            {105,730,57,50},   //49
            {385,730,43,51},    //50
            {625,730,45,52},    //51
            {885,730,47,53},    //52
            {1150,730,40,48},    //53

            {135,45,1},          //54
            {1120,45,4,13},      //55
            {1120,730,53},       //56
            {135,730,50,41 }     //57
    };

    // ������ infrastructure �������� ��������� �������� ����� �������� ��������������
    // ������ ���� ���������
    // ������ �������� ���������� �
    // ������ �������� ���������� Y
    public static final int [][] INFRASTRUCTURE =
            {{0,0},{165,230}, {345,230},{585,230},{685,230},{845,230}, {1080,230},{1180,230},
                    {345,540},{585,540},{685,540},{940,540},{1180,540}
            } ;
    public static final String [] NAME = {"","Office West Wing","Office East Wing","Cottage","Hospital West Wing","Hospital East Wing",
                                          "House", "Factory","Bank","Business center","City square","Golf","Shopping center"};

    public static final int [] TAXIstops = {0,14,15,16,17,18,20,21,36,37,38,39,40};

    //������ ���������� ����� ����� ��������� �������
    public static int distancePoint (int pozStart, int pozEnd ){
        int distance = (int) Math.sqrt(Math.pow(STREET[pozStart][0]- STREET[pozEnd][0],2)+Math.pow(STREET[pozStart][1]- STREET[pozEnd][1],2));

        return distance;
    }
}