package plugins;

import play.PlayPlugin;

public class S1Plugin extends PlayPlugin {
    
    
    @Override
    public void onLoad() {
        System.err.println("s1 onLoad");
    }
    
    @Override
    public void onApplicationReady() {
        super.onApplicationReady();
        System.err.println("s1 onApplicationReady");
    }
    
    @Override
    public void onApplicationStart() {
        super.onApplicationStart();
        System.err.println("s1 onApplicationStart");
    }
    
    @Override
    public void afterApplicationStart() {
        super.afterApplicationStart();
        System.err.println("s1 afterApplicationStart");
        
    }
    
    @Override
    public void onApplicationStop() {
        super.onApplicationStop();
        System.err.println("s1 onApplicationStop");
    }
    
}
