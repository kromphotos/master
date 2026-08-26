package com.kristina.treecrud;

public class Main {
    public final static Main SMALL = new Main(150);

    static class  Size {
        public final static Size SMALL = new Size(150);
        public final static Size MED = new Size(300);
        public final static Size BIG = new Size(450);
//        SMALL(150),
//        MED(300),
//        BIG(450);

        private int ml;

        Size(int ml) {
            this.ml = ml;
        }

        public int getMl() {
            return ml;
        }

        public void setMl(int ml) {
            this.ml = ml;
        }
        
        
    };

    int ml;

    Main(int ml) {
        this.ml = ml;
    }

    public static void main(String[] args) {
        int[] massiv;
        int[] massivv = { 1, 2, 3 };
        int[] massivvv = new int[3];
        int[][] tr = { { 1 }, { 2 }, { 3 } };
        int[][] massivvvv = new int[3][];
        
        

    }

}
