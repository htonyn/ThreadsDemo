package threadsdemo;

public class ContesterThread extends Thread {
    StringBuffer sb = null;
    
    public ContesterThread(StringBuffer sb) {
        this.sb = sb;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized(sb) {
                sb.append("iteration: ");
                sb.append(i);
                sb.append("\n");
            }
        }
    }
}