package net.rschetnan.bluehouse;

public enum Tokens {
	
	APPC("0x1a7a8bd9106f2b8d977e08582dc7d24c723ab0db", "AppCoins"),
	ADX("0x4470BB87d77b963A013DB939BE332f927f2b992e", "AdEx"),
	AE("0x5CA9a71B1d01849C0a95490Cc00559717fCF0D1d", "Aeternity"),
	ARN("0xBA5F11b16B155792Cf3B2E6880E8706859A8AEB6", "Aeron"),
	AST("0x27054b13b1B798B345b591a4d22e6562d47eA75a", "AirSwap"),
	BLZ("0x5732046A883704404F284Ce41FfADd5b007FD668", "Bluzelle Token"),
	BNT("0x1F573D6Fb3F13d689FF844B4cE37794d79a7FF1C", "Bancor Network Token"),
	CMT("0xf85fEea2FdD81d51177F6b8F35F0e6734Ce45F5F", "CyberMiles Token"),
	DLT("0x07e3c70653548B04f0A75970C1F81B4CBbFB606f", "Delta"),
	ELF("0xaB279D9F6934375f3208ECD730fa2B125Cc185Fa", "aelf"),
	DNT("0x0AbdAce70D3790235af448C88547603b945604ea", "district0x Network Token"),
	ENJ("0xF629cBd94d3791C9250152BD8dfBDF380E2a3B9c", "Enjin Coin"),
	GTO("0xC5bBaE50781Be1669306b9e001EFF57a2957b09d", "Gifto"),
	INS("0x5B2e4a700dfBc560061e957edec8F6EeEb74a320", "INS Token"),
	LRC("0xEF68e7C694F40c8202821eDF525dE3782458639f", "loopring"),
	LOOM("0xa4e8c3ec456107ea67d3075bf9e3df3a75823db0", "LoomToken"),
	MITH("0x3893b9422Cd5D70a81eDeFfe3d5A1c6A978310BB", "Mithril"),
	MTH("0xaF4DcE16Da2877f8c9e00544c93B62Ac40631F16", "Monetha"),
	OAX("0x701C244b988a513c945973dEFA05de933b23Fe1D", "openANX Token"),
	OST("0x2c4e8f2d746113d0696ce89b35f0d8bf88e0aeca", "Simple Token"),
	PERL("0xb5A73f5Fc8BbdbcE59bfD01CA8d35062e0dad801", "Perlin"),
	RCN("0xF970b8E36e23F7fC3FD752EeA86f8Be8D83375A6", "Ripio Credit Network Token"),
	RDN("0x255Aa6DF07540Cb5d3d297f0D0D4D84cb52bc8e6", "Raiden Token"),
	REP("0x1985365e9f78359a9B6AD760e32412f4a445E862", "Reputation"),
	STORJ("0xb64ef51c888972c908cfacf59b47c1afbc0ab8ac", "Storj"),
	SNGLS("0xaeC2E87E0A235266D9C5ADc9DEb4b2E29b54D009", "SingularDTV"),
	WPR("0x4CF488387F035FF08c371515562CBa712f9015d4", "WePower Token"),
	NKN("0x5Cf04716BA20127F1E2297AdDCf4B5035000c9eb", "NKN"),
	AMB("0x4dc3643dbc642b72c158e7f3d2ff232df61cb6ce", "Amber"),
	BCPT("0x1c4481750daa5Ff521A2a7490d9981eD46465Dbd", "BLOCKMASON CREDIT PROTOCOL TOKEN"),
	DGD("0xe0b7927c4af23765cb51314a0e0521a9645f0e2a", "DigixDAO"),
	OMG("0xd26114cd6EE289AccF82350c8d8487fedB8A0C07", "OmiseGO"),
	ZRX("0xe41d2489571d322189246dafa5ebde1f4699f498", "0x");
	
	private Tokens(String address, String name)
	{
		this.address = address;
		this.name = name;
	}
	
	
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	private String address;
	
	private String name;

}
