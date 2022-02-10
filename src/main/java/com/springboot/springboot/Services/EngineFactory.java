package com.springboot.springboot.Services;


public class EngineFactory {
    public EngineService createEngineServiceFactory(String engine)
    {
        EngineService engineService=null;
        switch (engine)
        {
            case "chineEngine":
                engineService=new ChineEngineImpl();
                break;
            case "vnEngine":
                engineService=new VnEngineImpl();
                break;
            default: new VnEngineImpl();
        }
    return engineService;
    }
}
