import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameTime {

    GameMap gameMap=new GameMap();

    public void time(int[][]  arr) throws Exception{
        Timer time=new Timer();//记录
//        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstTime=new Date();
        time.schedule(new TimerTask(){
            public void run(){
                int r=arr.length,c=arr[0].length;
                int [][]ay=gameMap.get_all_yi(arr);
                for (int i=0;i<r;i++){
                    for (int j=0;j<c;j++){
                        if(ay[i][j]==2){

                        }else if (ay[i][j]==3){
                            arr[i][j]=1;
                        }else {
                            arr[i][j]=0;
                        }
                    }
                }
                for (int i=0;i<r;i++){
                    for (int j=0;j<c;j++){
                        System.out.print(arr[i][j]+" ");
                    }
                    System.out.println();
                }
                System.out.println("----------------------------------------------------");
            }
        },firstTime,500);
    }

}
