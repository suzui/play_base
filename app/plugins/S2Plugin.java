package plugins;

import play.PlayPlugin;

public class S2Plugin extends PlayPlugin {
    
    
    @Override
    public void onLoad() {
        System.err.println("s2 onLoad");
    }
    
    @Override
    public void onApplicationReady() {
        super.onApplicationReady();
        System.err.println("s2 onApplicationReady");
    }
    
    @Override
    public void onApplicationStart() {
        super.onApplicationStart();
        System.err.println("s2 onApplicationStart");
    }
    
    @Override
    public void afterApplicationStart() {
        super.afterApplicationStart();
        System.err.println("s2 afterApplicationStart");
        
    }
    
    @Override
    public void onApplicationStop() {
        super.onApplicationStop();
        System.err.println("s2 onApplicationStop");
    }
    
    
}
