package net.rschetnan.bluehouse;

public enum Tokens {
	ANKR("Ankr Network", "0x8290333ceF9e6D528dD5618Fb97a76f268f3EDD4"),
	ARPA("ARPA Token", "0xBA50933C268F567BDC86E1aC131BE072C6B0b71a"),
	BAND("BandToken", "0xBA11D00c5f74255f56a5E366F4F77f5A186d7f55"),
	BAT("Basic Attention Token", "0x0D8775F648430679A709E98d2b0Cb6250d2887EF"),
	CELR("CelerToken", "0x4F9254C83EB525f9FCf346490bbb3ed28a81C667"),
	CHZ("chiliZ", "0x3506424F91fD33084466F402d5D97f05F8e3b4AF"),
	COCOS("CocosToken", "0x0C6f5F7D555E7518f6841a79436BD2b1Eef03381"),
	COS("COS", "0x7d3cb11f8c13730C24D01826d8F2005F0e1b348F"),
	CTXC("Cortex", "0xEa11755Ae41D889CeEc39A63E6FF75a02Bc1C00d"),
	CVC("Civic", "0x41e5560054824eA6B0732E656E3Ad64E20e94E45"),
	DENT("DENT", "0x3597bfD533a99c9aa083587B074434E61Eb0A258"),
	DOCK("DOCK", "0x2591f97ab8Db27cFE67ba0E582488f60Eff28277"),
	DUSK("Dusk Network", "0x940a2dB1B7008B6C776d4faaCa729d6d4A4AA551"),
	ENJ("Enjin Coin", "0xF629cBd94d3791C9250152BD8dfBDF380E2a3B9c"),
	FET("Fetch", "0x1D287CC25dAD7cCaF76a26bc660c5F7C8E2a05BD"),
	FTM("Fantom Token", "0x4E15361FD6b4BB609Fa63C81A2be19d873717870"),
	FTT("FTT", "0x50D1c9771902476076eCFc8B2A83Ad6b9355a4c9"),
	FUN("FunFair", "0x419D0d8BdD9aF5e606Ae2232ed285Aff190E711b"),
	GTO("Gifto", "0xC5bBaE50781Be1669306b9e001EFF57a2957b09d"),
	HOT("HoloToken", "0x6c6EE5e31d828De241282B9606C8e98Ea48526E2"),
	IOTX("IoTeX Network", "0x6fB3e0A217407EFFf7Ca062D46c26E5d60a14d69"),
	KAVA("Kava", "0x08d1E0A7fBd4eDBF56D81Da21D1b0c9c95Fb507F"),
	KEY("SelfKey", "0x4CC19356f2D37338b9802aa8E8fc58B0373296E7"),
	LINK("ChainLink Token", "0x514910771AF9Ca656af840dff83E8264EcF986CA"),
	MATIC("Matic Token", "0x7D1AfA7B718fb893dB30A3aBc0Cfc608AaCfeBB0"),
	MCO("Monaco", "0xB63B606Ac810a52cCa15e44bB630fd42D8d1d83d"),
	MFT("Mainframe Token", "0xDF2C7238198Ad8B389666574f2d8bc411A4b7428"),
	MITH("Mithril", "0x3893b9422Cd5D70a81eDeFfe3d5A1c6A978310BB"),
	MTL("Metal", "0xF433089366899D83a9f26A773D59ec7eCF30355e"),
	NKN("NKN", "0x5Cf04716BA20127F1E2297AdDCf4B5035000c9eb"),
	NPXS("Pundi X Token", "0xA15C7Ebe1f07CaF6bFF097D8a589fb8AC49Ae5B3"),
	OMG("OMGToken", "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07"),
	PERL("Perlin", "0xb5A73f5Fc8BbdbcE59bfD01CA8d35062e0dad801"),
	REN("Republic Token", "0x408e41876cCCDC0F92210600ef50372656052a38"),
	RLC("iEx.ec Network Token", "0x607F4C5BB672230e8672085532f7e901544a7375"),
	STORM("Storm Token", "0xD0a4b8946Cb52f0661273bfbC6fD0E0C75Fc6433"),
	TROY("TROY", "0x91a09E43EeEdbceEfBD5928361d1f917818E3A3B"),
	ZRX("0x Protocol Token", "0xE41d2489571d322189246DaFA5ebDe1F4699F498");
	
	private Tokens(String name, String address)
	{
		this.name = name;
		this.address = address;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	private String address;
	private String name;

}
