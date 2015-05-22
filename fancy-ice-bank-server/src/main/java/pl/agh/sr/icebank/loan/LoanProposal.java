package pl.agh.sr.icebank.loan;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class LoanProposal {
    private final float interestRate;
    private final int totalCost;

    public LoanProposal(int totalCost, float interestRate) {
        this.totalCost = totalCost;
        this.interestRate = interestRate;
    }

    public float interestRate() {
        return interestRate;
    }

    public int totalCost() {
        return totalCost;
    }
}
