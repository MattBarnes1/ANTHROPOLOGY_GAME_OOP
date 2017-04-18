/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AIStuff;

/**
 *
 * @author noone
 */
public abstract class StateExecution {
    protected AIHandler myHandler;
    protected boolean shouldExecute = false;
    protected boolean isFinished = false;
    public abstract void onEnter();
    public abstract void Execute();
    public abstract void onExit();
    public abstract void onFinish();
    public abstract StateExecution substateCheck();
    
     
    
    
    public boolean isFinished() {
        return this.isFinished;
    }
    
    //can we possibly add a check function here that could alter the states
    
    /*public StateExecution(AIHandler theOneCreatingUs)
    {
        myHandler = theOneCreatingUs;
        myHandler
    }*/
    

}
