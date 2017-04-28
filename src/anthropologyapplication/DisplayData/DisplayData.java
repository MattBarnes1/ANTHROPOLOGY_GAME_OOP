/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.MainGameCode;

/**
 *
 * @author noone
 */
public interface DisplayData {
    public String getName();
    public String getTimeToCompleteString();
    public double getCompletionPercentage();
    public void acceptRemoverAsVisitor(MainGameCode myGameCode);
    public String getDisplayedText();
    public boolean shouldBeRemoved();

    public String getToolTipString();
}
