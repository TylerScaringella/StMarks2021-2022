import java.util.Arrays;

public class Contagious {

    public static void main(String[] args) {
        new Contagious();
    }

    public Contagious() {
        boolean[] arr = {true, true, true};
//        boolean[] arr = {true, true, true, false, true, true, true};

        System.out.println(solve(arr));
    }

    public int solve(boolean[] arr) {
        int steps = -1;


        // go through and find locations of true and falses
        int loop = 0;
        while(loop < arr.length) {
            boolean didStep = false;
            for(int i=0; i<arr.length; i++) {
                if(!arr[i]) {
                    // is contagious | ones around need to be made false

                    // left of
                    if(i > 0) {
                        int leftIndex = i-1;
                        if(arr[leftIndex]) {
//                            System.out.println("Setting index " + leftIndex + " to false");
                            arr[leftIndex] = false;
                            didStep = true;
                            loop = 0;
                        }
                    }

                    // right of
                    if(i < (arr.length - 1)) {
                        int rightIndex = i+1;
                        if(arr[rightIndex]) {
//                            System.out.println("Setting index " + rightIndex + " to false");
                            arr[rightIndex] = false;
                            didStep = true;
                            loop = 0;
                        }
                    }
                }
            }
            if(didStep) steps++;
            loop++;
        }

//        System.out.println(Arrays.toString(arr));

        return steps == -1 ? -1 : steps + 1;
    }
}