package com.abcimentos.model;

public enum Estado {
	
	RO(11,"Rond�nia"),
	AC(12,"Acre"),
	AM(13,"Amazonas"),
	RR(14,"Roraima"),
	PA(15,"Par�"),
	AP(16,"Amap�"),
	TO(17,"Tocantins"),
	MA(21,"Maranh�o"),
	PI(22,"Piau�"),
	CE(23,"Cear�"),
	RN(24,"Rio Grande do Norte"),
	PB(25,"Para�ba"),
	PE(26,"Pernambuco"),
	AL(27,"Alagoas"),
	SE(28,"Sergipe"),
	BA(29,"Bahia"),
	MG(31,"Minas Gerais"),
	ES(32,"Esp�rito Santo"),
	RJ(33,"Rio de Janeiro"),
	SP(35,"S�o Paulo"),
	PR(41,"Paran�"),
	SC(42,"Santa Catarina"),
	RS(43,"Rio Grande do Sul"),
	MS(50,"Mato Grosso do Sul"),
	MT(51,"Mato Grosso"),
	GO(52,"Goi�s"),
	DF(53,"Distrito Federal");
	
	private int value;
	private String label;
	
	Estado(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
	
	// retorna um perfil a partir de um valor inteiro
	public static Estado valueOf(int value) {
		for (Estado estado : Estado.values()) {
			if (estado.getValue() == value) {
				return estado;
			}
		}
		return null;
	}
}
