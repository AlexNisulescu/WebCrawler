public class Main {
    public static void main(String[] args)
    {
        for(int i=0;i<args.length;i++){
            if (args[i].equals("crawl")){
                String cfg=args[i+1];
                String Urls=args[i+2];
                Config config=new Config();
                config.recursiveDownload(cfg,Urls);
                try {
                    SiteMap x=SiteMap.getInstance();
                    x.printSitemap("/",0);
                }
                catch (FileNotFoundException e){
                    e.throwExc();
                }
            }
            if (args[i].equals("search")){
                String tip=args[i+1];
                String cale =args[i+2];
                Search a= new TypeFilter(cale);
                try {
                    a.search_method(tip);
                }
                catch (FileNotFoundException e)
                {
                    e.throwExc();
                }
            }
            if (args[i].equals("list")){
                String list=args[i+1];
                String cale =args[i+2];
                Search b=new WordFilter(cale);
                try {
                    b.search_method(list);
                }
                catch (FileNotFoundException e)
                {
                    e.throwExc();
                }
            }
        }
    }
}