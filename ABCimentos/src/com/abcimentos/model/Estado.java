package com.abcimentos.model;

public enum Estado {
	
	RO(11,"Rondônia"),
	AC(12,"Acre"),
	AM(13,"Amazonas"),
	RR(14,"Roraima"),
	PA(15,"Pará"),
	AP(16,"Amapá"),
	TO(17,"Tocantins"),
	MA(21,"Maranhão"),
	PI(22,"Piauí"),
	CE(23,"Ceará"),
	RN(24,"Rio Grande do Norte"),
	PB(25,"Paraíba"),
	PE(26,"Pernambuco"),
	AL(27,"Alagoas"),
	SE(28,"Sergipe"),
	BA(29,"Bahia"),
	MG(31,"Minas Gerais"),
	ES(32,"Espírito Santo"),
	RJ(33,"Rio de Janeiro"),
	SP(35,"São Paulo"),
	PR(41,"Paraná"),
	SC(42,"Santa Catarina"),
	RS(43,"Rio Grande do Sul"),
	MS(50,"Mato Grosso do Sul"),
	MT(51,"Mato Grosso"),
	GO(52,"Goiás"),
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
