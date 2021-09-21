package challenge;

import java.util.Arrays;

public class Challenge {

    public static void main(String[] args) {
        // array can be size n^2
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int n = (int) Math.floor(Math.sqrt(nums.length));
        int[][] nums2d = new int[n][n];

        int cur = 0;

        int top = 0, bottom = n-1;
        int left = 0, right = n-1;

        while(cur < nums.length) {
            if(left > right) break;

            for(int i=left; i<= right; i++) {
                nums2d[top][i] = nums[cur++];
            }
            top++;

            if(top > bottom) break;

            for(int i=top; i<=bottom; i++) {
                nums2d[i][right] = nums[cur++];
            }
            right--;

            if(left > right) break;

            for(int i=right; i>=left; i--) {
                nums2d[bottom][i] = nums[cur++];
            }
            bottom--;

            if(top > bottom) break;

            for(int i=bottom; i>=top; i--) {
                nums2d[i][left] = nums[cur++];
            }
            left++;
        }

        int[] reverse = new int[n*n];

        cur = 0;
        for(int i=0; i<n; i++) {
            for(int x=0; x<n; x++) {
                reverse[cur] = nums2d[i][x];
                cur++;
            }
        }

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("2d Array:");
        for(int i=0; i<n; i++) {
            System.out.println(Arrays.toString(nums2d[i]));
        }
        System.out.println("Array: " + Arrays.toString(reverse));
    }
}
