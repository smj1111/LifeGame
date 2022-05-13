public class LifeGame {

    int [][] game_map;

    GameMap gameMap=new GameMap();

    GameTime gameTime=new GameTime();

    int sl;

    void init(int r,int c){
        game_map=gameMap.init(r,c);
    }

    void init(int r,int c,int sl){
        game_map=gameMap.init(r,c);
        this.sl=sl;
    }

    void circle_one(int [][] arr){
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
//        for (int i=0;i<r;i++){
//            for (int j=0;j<c;j++){
//                System.out.print(arr[i][j]+" ");
//            }
//            System.out.println();
//        }
    }

    void run(int[][] arr) throws Exception {
        gameTime.time(arr);
    }

//    void ru(LifeGame lifeGame) throws Exception {
//        int m=Integer.parseInt(this.m),n=Integer.parseInt(this.n);
//        lifeGame.init(m,n);
//        lifeGame.run(lifeGame.game_map);
//    }

    public static void main(String[] args) throws Exception {
        LifeGame lifeGame=new LifeGame();
        lifeGame.init(30,30);
        lifeGame.run(lifeGame.game_map);
    }

    public boolean gameOfLife() {
        for(int i=0;i<game_map.length;i++){
            for(int j=0;j<game_map[0].length;j++){
                if(game_map[i][j]==1)return true;
            }
        }
        return false;
    }
}
