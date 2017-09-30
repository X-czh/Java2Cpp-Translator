package inputs.test031;

public class Test031 {
  public static void main(String[] args) {
    int[][] as = new int[5][5]; 
    
    for (int i = 0; i < as.length; i++) {
      for (int j = 0; j < as[i].length; j++) {
        as[i][j] = i * j;
      }
    }

    for (int i = 0; i < as.length; i++) {
      for (int j = 0; j < as[i].length; j++) {
        System.out.println(as[i][j]);
      }
    }
    
  }
}
