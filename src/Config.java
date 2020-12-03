public class Config {
    int nThreads;
    String root;
    int logLevel;
    int delay;

    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getnThreads() {
        return nThreads;
    }

    public String getRoot() {
        return root;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public int getDelay() {
        return delay;
    }

    public void recursiveDownload(String conf){

    }
    public void parse(){

    }

}
