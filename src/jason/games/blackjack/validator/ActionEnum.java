package jason.games.blackjack.validator;

public enum ActionEnum {
	STAND("S"), HIT("H"), DOUBLE("D"), SPLIT("T");
	
	private String shortCut;

	ActionEnum(String shortCut) {
		this.shortCut = shortCut;
	}	

	public String getShortCut() {
		return shortCut;
	}

	public static ActionEnum getAction(String input) {
		
		if (HIT.name().equalsIgnoreCase(input) || HIT.getShortCut().equalsIgnoreCase(input)) {
			return HIT;
		}
		
		if (STAND.name().equalsIgnoreCase(input) || STAND.getShortCut().equalsIgnoreCase(input)) {
			return STAND;
		}
		
		if (SPLIT.name().equalsIgnoreCase(input) || SPLIT.getShortCut().equalsIgnoreCase(input)) {
			return SPLIT;
		}
		
		if ((DOUBLE.name().equalsIgnoreCase(input) || "double down".equalsIgnoreCase(input) 
				|| "double-down".equalsIgnoreCase(input) || DOUBLE.getShortCut().equalsIgnoreCase(input))) {
			return DOUBLE;
		}
		
		return null;
	}
}