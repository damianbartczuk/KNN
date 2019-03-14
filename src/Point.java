public class Point implements Comparable<Point>{
    private String line;
    private double result;


    public String getLine() {
        return line;
    }

    public Point(String line, double result) {
        this.line = line;
        this.result = result;
    }

    @Override
    public int compareTo(Point o) {
        if (this.result > o.result){
            return 1;
        }else if(this.result < o.result){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Point{" +
                "line='" + line + '\'' +
                ", result=" + result +
                '}';
    }
}
