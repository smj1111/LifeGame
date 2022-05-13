import java.util.Random;

public class GameMap {

    int[][] init(int r,int c){
        int[][] re=new int[r][c];
        Random random=new Random();
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                boolean in=random.nextBoolean();
                if(in)re[i][j]=0;
                else re[i][j]=1;
            }
        }
        return re;
    }

    void set(int [][] arr,int r,int c,int v){
        arr[r][c]=v;
    }

    int get(int [][] arr,int r,int c){
        int i=arr.length,j=arr[0].length;
        int yi=0;
        for(int m=r-1;m<=r+1;m++){
            for (int n=c-1;n<c+2;n++){
                if(m==r&&n==c)continue;
                if(m>=0&&m<i&&n>=0&&n<j){
                    yi+=arr[m][n];
                }
            }
        }
        return yi;
    }

    void reset(int[][] arr){
        int r=arr.length,c=arr[0].length;
        Random random=new Random();
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                boolean in=random.nextBoolean();
                if(in)arr[i][j]=0;
                else arr[i][j]=1;
            }
        }
    }

    int [][]get_all_yi(int [][] arr){
        int r=arr.length,c=arr[0].length;
        int [][] re=new int[r][c];
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                if(arr[i][j]==1){
                    for(int m=i-1;m<=i+1;m++){
                        for (int n=j-1;n<j+2;n++){
                            if(m==i&&n==j) {
                                continue;
                            }
                            if(m>=0&&m<r&&n>=0&&n<c){
                                re[m][n]++;
                            }
                        }
                    }
                }
            }
        }
        return re;
    }

}
