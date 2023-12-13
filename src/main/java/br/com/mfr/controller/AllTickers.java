package br.com.mfr.controller;

import java.util.List;

public interface AllTickers {

	public static final List<String> ACOES = List.of("AALR3", "ABCB4", "ABEV3", "ADHM3", "AFLT3", "AGRO3",
			"AHEB3", "AHEB5", "AHEB6", "ALPA3", "ALPA4", "ALPK3", "ALSC3", "ALSO3", "ALUP11", "ALUP3", "ALUP4", "AMAR3",
			"ANDG3B", "ANDG4B", "ANIM3", "APER3", "APTI3", "APTI4", "ARZZ3", "ATMP3", "ATOM3", "AZEV3", "AZEV4",
			"AZUL4", "B3SA3", "BAHI3", "BALM3", "BALM4", "BAUH4", "BAZA3", "BBAS3", "BBDC3", "BBDC4", "BBML3", "BBRK3",
			"BBSE3", "BDLL3", "BDLL4", "BEEF3", "BEES3", "BEES4", "BFRE11", "BFRE12", "BGIP3", "BGIP4", "BIDI11",
			"BIDI3", "BIDI4", "BIOM3", "BKBR3", "BMEB3", "BMEB4", "BMGB4", "BMIN3", "BMIN4", "BMKS3", "BNBR3", "BOBR3",
			"BOBR4", "BPAC11", "BPAC3", "BPAC5", "BPAN4", "BPAR3", "BPAT33", "BPHA3", "BRAP3", "BRAP4", "BRDT3",
			"BRFS3", "BRGE11", "BRGE12", "BRGE3", "BRGE5", "BRGE6", "BRGE7", "BRGE8", "BRIV3", "BRIV4", "BRKM3",
			"BRKM5", "BRKM6", "BRML3", "BRPR3", "BRQB3", "BRSR3", "BRSR5", "BRSR6", "BSEV3", "BSLI3", "BSLI4", "BTOW3",
			"BTTL3", "BTTL4", "CALI3", "CALI4", "CAMB3", "CAMB4", "CAML3", "CARD3", "CASN3", "CASN4", "CATA3", "CATA4",
			"CBEE3", "CCPR3", "CCRO3", "CCXC3", "CEAB3", "CEBR3", "CEBR5", "CEBR6", "CEDO3", "CEDO4", "CEEB3", "CEEB5",
			"CEEB6", "CEED3", "CEED4", "CEGR3", "CEPE3", "CEPE5", "CEPE6", "CESP3", "CESP5", "CESP6", "CGAS3", "CGAS5",
			"CGRA3", "CGRA4", "CIEL3", "CLSC3", "CLSC4", "CMIG3", "CMIG4", "CMSA3", "CMSA4", "CNSY3", "CNTO3", "COCE3",
			"COCE5", "COCE6", "COGN3", "CORR3", "CORR4", "CPFE3", "CPLE3", "CPLE5", "CPLE6", "CPRE3", "CRDE3", "CREM3",
			"CRFB3", "CRIV3", "CRIV4", "CRPG3", "CRPG5", "CRPG6", "CSAB3", "CSAB4", "CSAN3", "CSMG3", "CSNA3", "CSRN3",
			"CSRN5", "CSRN6", "CTCA3", "CTKA3", "CTKA4", "CTNM3", "CTNM4", "CTSA3", "CTSA4", "CTSA8", "CVCB3", "CYRE3",
			"DASA3", "DIRR3", "DMMO3", "DOHL3", "DOHL4", "DTCY3", "DTCY4", "DTEX3", "EALT3", "EALT4", "ECOR3", "ECPR3",
			"ECPR4", "EEEL3", "EEEL4", "EGIE3", "EKTR3", "EKTR4", "ELEK3", "ELEK4", "ELET3", "ELET5", "ELET6", "ELPL3",
			"EMAE3", "EMAE4", "EMBR3", "ENAT3", "ENBR3", "ENEV3", "ENGI11", "ENGI3", "ENGI4", "ENMA3B", "ENMA6B",
			"ENMT3", "ENMT4", "EQPA3", "EQPA5", "EQPA6", "EQPA7", "EQTL3", "ESTR3", "ESTR4", "ETER3", "EUCA3", "EUCA4",
			"EVEN3", "EZTC3", "FBMC3", "FBMC4", "FESA3", "FESA4", "FHER3", "FIGE3", "FIGE4", "FLEX3", "FLRY3", "FNCN3",
			"FRAS3", "FRIO3", "FRTA3", "FTRT3B", "GBIO33", "GEPA3", "GEPA4", "GFSA3", "GGBR3", "GGBR4", "GNDI3",
			"GOAU3", "GOAU4", "GOLL4", "GPAR3", "GPCP3", "GPCP4", "GPIV33", "GRND3", "GSHP3", "GUAR3", "HAGA3", "HAGA4",
			"HAPV3", "HBOR3", "HBTS5", "HETA3", "HETA4", "HGTX3", "HOOT3", "HOOT4", "HYPE3", "IDNT3", "IDVL3", "IDVL4",
			"IGBR3", "IGSN3", "IGTA3", "INEP3", "INEP4", "INNT3", "IRBR3", "ITEC3", "ITSA3", "ITSA4", "ITUB3", "ITUB4",
			"JBDU3", "JBDU4", "JBSS3", "JFEN3", "JHSF3", "JOPA3", "JOPA4", "JPSA3", "JSLG3", "KEPL3", "KLBN11", "KLBN3",
			"KLBN4", "LAME3", "LAME4", "LCAM3", "LEVE3", "LHER3", "LHER4", "LIGT3", "LINX3", "LIPR3", "LLIS3", "LOGG3",
			"LOGN3", "LPSB3", "LREN3", "LTEL3B", "LUPA3", "LUXM3", "LUXM4", "LWSA3", "MAPT3", "MAPT4", "MDIA3", "MDNE3",
			"MEAL3", "MERC3", "MERC4", "MGEL3", "MGEL4", "MGLU3", "MILS3", "MMAQ3", "MMAQ4", "MMXM3", "MNDL3", "MNPR3",
			"MOAR3", "MOVI3", "MRFG3", "MRSA3B", "MRSA5B", "MRSA6B", "MRVE3", "MSPA3", "MSPA4", "MSRO3", "MTIG3",
			"MTIG4", "MTRE3", "MTSA3", "MTSA4", "MULT3", "MWET3", "MWET4", "MYPK3", "NAFG3", "NAFG4", "NEMO3", "NEMO5",
			"NEMO6", "NEOE3", "NORD3", "NRTQ3", "NTCO3", "NUTR3", "ODER4", "ODPV3", "OFSA3", "OGXP3", "OIBR3", "OIBR4",
			"OMGE3", "OSXB3", "PARD3", "PATI3", "PATI4", "PCAR3", "PCAR4", "PDGR3", "PEAB3", "PEAB4", "PETR3", "PETR4",
			"PFRM3", "PINE3", "PINE4", "PLAS3", "PMAM3", "PNVL3", "PNVL4", "POMO3", "POMO4", "POSI3", "PPAR3", "PPLA11",
			"PRIO3", "PRNR3", "PSSA3", "PTBL3", "PTCA11", "PTCA3", "PTNT3", "PTNT4", "QUAL3", "QUSW3", "QVQP3B",
			"RADL3", "RAIL3", "RANI3", "RANI4", "RAPT3", "RAPT4", "RCSL3", "RCSL4", "RDNI3", "REDE3", "RENT3", "RLOG3",
			"RNEW11", "RNEW3", "RNEW4", "ROMI3", "RPAD3", "RPAD5", "RPAD6", "RPMG3", "RSID3", "RSUL3", "RSUL4",
			"SANB11", "SANB3", "SANB4", "SAPR11", "SAPR3", "SAPR4", "SBSP3", "SCAR3", "SEDU3", "SEER3", "SGPS3",
			"SHOW3", "SHUL3", "SHUL4", "SLCE3", "SLED3", "SLED4", "SMFT3", "SMLS3", "SMTO3", "SNSY3", "SNSY5", "SNSY6",
			"SOND3", "SOND5", "SOND6", "SPRI3", "SPRI5", "SPRI6", "SPRT3B", "SQIA3", "STBP3", "STKF3", "STTR3",
			"SULA11", "SULA3", "SULA4", "SUZB3", "TAEE11", "TAEE3", "TAEE4", "TASA3", "TASA4", "TCNO3", "TCNO4",
			"TCSA3", "TECN3", "TEKA3", "TEKA4", "TELB3", "TELB4", "TEND3", "TESA3", "TGMA3", "TIET11", "TIET3", "TIET4",
			"TIMP3", "TKNO3", "TKNO4", "TOTS3", "TOYB3", "TOYB4", "TPIS3", "TRIS3", "TRPL3", "TRPL4", "TUPY3", "TXRX3",
			"TXRX4", "UCAS3", "UGPA3", "UNIP3", "UNIP5", "UNIP6", "USIM3", "USIM5", "USIM6", "VALE3", "VIVA3", "VIVR3",
			"VIVT3", "VIVT4", "VLID3", "VSPT3", "VSPT4", "VULC3", "VVAR3", "WEGE3", "WHRL3", "WHRL4", "WIZS3", "WLMM3",
			"WLMM4", "WSON33", "YDUQ3");

	public static final List<String> FIIS = List.of("CXTL11", "RBFF11", "LUGG11", "TBOF11", "PATC11", "BRCO11",
			"HGBS11", "BARI11", "ALZR11", "RBBV11", "RBRF11", "RBDS11", "LVBI11", "FCFL11", "HUSC11", "FISD11",
			"CRFF11", "FINF11", "XPML11", "EDFO11B", "MXRF11", "ONEF11", "SDIL11", "FAMB11B", "VPSI11", "DOMC11",
			"OULG11", "RBRP11", "XPLG11", "VINO11", "CPTS11B", "GSFI11", "NVIF11B", "LATR11B", "VSEC11", "XPHT11",
			"BPML11", "IRDM11", "TOUR11", "PQDP11", "DRIT11B", "FPAB11", "BBRC11", "BLMO11", "HUSI11", "OUFF11",
			"DOVL11B", "MGHT11", "TSNC11", "XPCI11", "HLOG11", "CARE11", "REIT11", "RBVO11", "HABT11", "SPAF11",
			"ALMI11", "CBOP11", "CTXT11", "BPFF11", "RBTS11", "PRSV11", "BRHT11B", "VGIR11", "GESE11B", "HCRI11",
			"RBCB11", "XPSF11", "TORM13", "FVBI11", "CEOC11", "SADI11", "PBLV11", "GGRC11", "BBIM11", "HGRE11",
			"BMLC11B", "RBGS11", "MBRF11", "HGRU11", "LGCP11", "PLCR11", "VISC11", "NPAR11", "LASC11", "GTWR11",
			"NVHO11", "RBIV11", "FLRP11", "BCIA11", "FPNG11", "BREV11", "RBVA11", "HGFF11", "TORD11", "UBSR11",
			"BLCP11", "RBRM11", "CXCE11B", "JSRE11", "XPCM11", "KNRE11", "HTMX11", "FCAS11", "SHPH11", "MAXR11",
			"RBRD11", "LOFT11B", "RNDP11", "BBFI11B", "RSPD11", "RBIR11", "RECT11", "MALL11", "MORE11", "KNRI11",
			"CNES11", "FTCE11B", "HSML11", "AFCR11", "TFOF11", "CVBI11", "THRA11", "VXXV11", "FLMA11", "RNGO11",
			"PATB11", "SAIC11B", "BMII11", "GRLV11", "BVAR11", "RBRL11", "XTED11", "FAED11", "BZLI11", "RFOF11",
			"SHDP11B", "SPTW11", "ABCP11", "XPPR11", "PRTS11", "PABY11", "FIIB11", "BTLG11", "BPRP11", "ANCR11B",
			"JPPC11", "HMOC11", "AQLL11", "WPLZ11", "RVBI11", "PORD11", "EURO11", "HCTR11", "HBTT11", "CPFF11",
			"FOFT11", "NEWL11", "HGPO11", "KINP11", "VERE11", "KNHY11", "SCPF11", "FIVN11", "KFOF11", "HGLG11",
			"ATSA11", "BCFF11", "VIFI11", "SHOP11", "ERPA11", "PLRI11", "VOTS11", "VSHO11", "FEXC11", "TGAR11",
			"VTLT11", "MFII11", "RCRB11", "ARRI11", "KNIP11", "BBPO11", "SPVJ11", "FMOF11", "DAMT11B", "FIGS11",
			"DMAC11", "VVPR11", "YCHY11", "FIIP11B", "MGFF11", "MCCI11", "JRDM11", "ORPD11", "RBED11", "HBRH11",
			"JTPR11", "TCPF11", "XPIN11", "RDPD11", "VTPL11", "VRTA11", "VILG11", "TRNT11", "URPR11", "NSLU11",
			"BICR11", "KNCR11", "SAAG11", "STRX11", "FVPQ11", "HPDP11", "BRCR11", "TEPP11", "ATCR11", "HFOF11",
			"HRDF11", "OUJP11", "VCJR11", "RCRI11B", "HGCR11", "FISC11", "BTCR11", "EDGA11", "TRXF11", "SARE11",
			"BNFS11", "VLOL11", "CXRI11", "OUCY11", "ARFI11B", "RBRR11", "VLJS11", "BCRI11", "NEWU11", "IBFF11",
			"SFND11", "ELDO11B", "RBRY11", "RBCO11", "JPPA11", "BBVJ11", "NCHB11", "QAGR11", "VGIP11", "WTSP11B",
			"PRSN11B", "HOSI11", "RCFA11");

	public static final List<String> ETFS = List.of("BBSD11", "XBOV11", "IVVB11", "BOVA11", "BRAX11", "ECOO11",
			"SMAL11", "BOW11", "DIVO11", "FIND11", "FIXA11", "GOVE11", "IMAB11", "MATB11", "ISUS11", "PIBB11",
			"SPXI11");

}