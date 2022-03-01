package comparers;

public class DifferenceOperatorFactory {
	IDifferenceOperator operator;

	public IDifferenceOperator getOperator() {
		return operator;
	}

	public void setOperator(IDifferenceOperator operator) {
		this.operator = operator;
	}
}
