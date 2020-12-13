public class Main {
    public static void main(String[] args)
    {
        for(int i=0;i<args.length;i++){
            if (args[i].equals("crawl")){
                String cfg=args[i+1];
                String Urls=args[i+2];
                Config config=new Config();
                config.recursiveDownload(cfg,Urls);
            }
            if (args[i].equals("search")){
                String tip=args[i+1];
                String cale =args[i+2];
                Search a=TypeFilter(String cale)
                a.search_method(tip)
            }
            if (args[i].equals("list")){
                String list=args[i+1];
                String cale =args[i+2];
                Search b=WordFilter(String cale)
                b.search_method(String list)
            }
        }
    }
}
