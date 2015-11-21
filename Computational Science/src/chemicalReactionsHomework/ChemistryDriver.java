package chemicalReactionsHomework;

import java.awt.Color;

public class ChemistryDriver {
	//container properties
	static private final double size = 50;
	static private final double reactionRadius = 4;
	static private final double meanFreeTime = 1;
	static private final double temperature = 313.15;
	static private final double deltaTime = 0.005;
	//
	private static final double AVOGADROS_NUMBER = 6.023e26;
	//NH3 properties
	static private final double massNH3 = 2.82e-23;
	static private final Color colorNH3 = Color.RED;
	//HCl properties
	static private final double massHCl = 36.4609/AVOGADROS_NUMBER;
	static private final Color colorHCl = Color.BLUE;
	//NH4Cl
	static private final double massNH4Cl = 53.491/AVOGADROS_NUMBER;
	static private final Color colorNH4Cl = Color.GREEN;
	//CO2 properties
	static private final double massCO2 = 44.0095/AVOGADROS_NUMBER;
	static private final Color colorCO2 = Color.YELLOW;
	//H2O properties
	static private final double massH2O = 18.02/AVOGADROS_NUMBER;
	static private final Color colorH2O = Color.CYAN;
	//HCO3- properties
	static private final double massHCO3 = 61.0168/AVOGADROS_NUMBER;
	static private final Color colorHCO3 = Color.PINK;
	//H3O+ properties
	static private final double massH3O = 19.02322/AVOGADROS_NUMBER;
	static private final Color colorH3O = Color.ORANGE;
	//
    private static final double massCarbonicAnhydrase = 29200 / AVOGADROS_NUMBER;
    private static final Color colorCarbonicAnhydrase = Color.GRAY;

    /**
     * Variables for the challenge. CoA family. In shades of purple.
     */
    private static final double massAcetylCoA = 809.571 / AVOGADROS_NUMBER;
    private static final Color colorAcetylCoA = new Color(100,0,100);
    private static final double massHSCoA = 768.545 / AVOGADROS_NUMBER;
    private static final Color colorHSCoA = new Color(150,0,150);

    /**
     * Variables for the challenge. The main molecule. In shades of red.
     */
    private static final double massOxaloacetate = 130.05568 / AVOGADROS_NUMBER; //checked
    private static final Color colorOxaloacetate = new Color(255,0,0);
    private static final double massCitrate = 189.101 / AVOGADROS_NUMBER;
    private static final Color colorCitrate = new Color(225,0,0);
    private static final double massCisAconitate = 171.081 / AVOGADROS_NUMBER;
    private static final Color colorCisAconitate = new Color(195,0,0);
    private static final double massIsocitrate = massCitrate;
    private static final Color colorIsocitrate = new Color(165,0,0);
    private static final double massAlphaKetoglutarate = 143.0715 / AVOGADROS_NUMBER;
    private static final Color colorAlphaKetoglutarate = new Color(135,0,0);
    private static final double massSuccinylCoA = 867.608 / AVOGADROS_NUMBER;
    private static final Color colorSuccinylCoA = new Color(105,0,0);
    private static final double massSuccinate = 116.073 / AVOGADROS_NUMBER;
    private static final Color colorSuccinate = new Color(75,0,0);
    private static final double massFumarate = 114.057 / AVOGADROS_NUMBER;
    private static final Color colorFumarate = new Color(45,0,0);
    private static final double massMalate = 132.077 / AVOGADROS_NUMBER;
    private static final Color colorMalate = new Color(15,0,0);

    /**
     * Variables for the challenge. The NAD/NADH group. In shades of yellow.
     */
    private static final double massNAD = 663.43 / AVOGADROS_NUMBER;
    private static final Color colorNAD = new Color(100,100,0);
    private static final double massNADH = 664.44 / AVOGADROS_NUMBER;
    private static final Color colorNADH = new Color(170,170,0);
    private static final double massH = 1.01 / AVOGADROS_NUMBER;
    private static final Color colorH = new Color(225,225,0);

    /**
     * Variables for the challenge. The GDP/GTP family. In shades of green.
     */
    private static final double massGDP = 443.2005 / AVOGADROS_NUMBER;
    private static final Color colorGDP = new Color(0,100,0);
    private static final double massP = 94.9714 / AVOGADROS_NUMBER;
    private static final Color colorP = new Color(0,150,0);
    private static final double massGTP = 523.18 / AVOGADROS_NUMBER;
    private static final Color colorGTP = new Color(0,200,0);

    /**
     * Variables for the challenge. The FAD family. In shades of light blue.
     */
    private static final double massFAD = 707.57 / AVOGADROS_NUMBER;
    private static final Color colorFAD = new Color(2,196,211);
    private static final double massFADH2 = 785.55 / AVOGADROS_NUMBER;
    private static final Color colorFADH2 = new Color(135,196,250);
    
    /**
     * Variables for the unitAssessment. Includes mass of ions. H2O and H are included already above.
     */
    private static final double massMgOH2 = 58.32 / AVOGADROS_NUMBER;
    private static final Color colorMgOH2 = new Color(0, 150, 0);
    private static final double massMg = 24.3 / AVOGADROS_NUMBER;
    private static final Color colorMg = Color.RED;
    private static final double massOH = 17 / AVOGADROS_NUMBER;
    private static final Color colorOH = Color.BLUE;
    private static final double massCl = 35.453 / AVOGADROS_NUMBER;
    private static final Color colorCl = Color.CYAN;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//level1_1();
		//level2_1();
		//level3_1();
		//challenge();
		unitAssessment();
	}
	
	public static void level1_1()
	{
		ParticleContainer container = new ParticleContainer(size, size, size, reactionRadius);
		container.getDictionary().addParticle("NH3", massNH3, colorNH3, meanFreeTime);
		container.getDictionary().addParticle("HCl", massHCl, colorHCl, meanFreeTime);
		container.getDictionary().addParticle("NH4Cl", massNH4Cl, colorNH4Cl, meanFreeTime);
		container.getReactionDictionary().addReaction("NH3+HCl=NH4Cl", 0, null, 1);
		RandomGenerator generator = new BoltzmannGenerator(container, temperature);
		final int nPart = 1000;
		container.addRandomParticles(generator, nPart, "NH3");
		container.addRandomParticles(generator, 2*nPart, "HCl");
		container.run(deltaTime);
	}
	
	public static void level2_1()
	{
		ParticleContainer container = new ParticleContainer(size, size, size, reactionRadius);
		container.getDictionary().addParticle("H2O", massH2O, colorH2O, meanFreeTime);
		container.getDictionary().addParticle("CO2", massCO2, colorCO2, meanFreeTime);
		container.getDictionary().addParticle("H3O", massH3O, colorH3O, meanFreeTime);
		container.getDictionary().addParticle("HCO3", massHCO3, colorHCO3, meanFreeTime);
		container.getReactionDictionary().addReaction("H2O+H2O+CO2=H3O+HCO3", 0, null, 1);
		container.getReactionDictionary().addReaction("H3O+HCO3=H2O+H2O+CO2", 0, null, 1);
		RandomGenerator generator = new BoltzmannGenerator(container, temperature);
		final int nPart = 1000;
		container.addRandomParticles(generator, nPart, "H2O");
		container.addRandomParticles(generator, nPart, "CO2");
		container.run(deltaTime);
	}
	
	public static void level3_1()
	{
		ParticleContainer container = new ParticleContainer(size, size, size, reactionRadius);
		container.getDictionary().addParticle("H2O", massH2O, colorH2O, meanFreeTime);
		container.getDictionary().addParticle("CO2", massCO2, colorCO2, meanFreeTime);
		container.getDictionary().addParticle("H3O", massH3O, colorH3O, meanFreeTime);
		container.getDictionary().addParticle("HCO3", massHCO3, colorHCO3, meanFreeTime);
		container.getDictionary().addParticle("CAT", massCarbonicAnhydrase, colorCarbonicAnhydrase, meanFreeTime);
		container.getReactionDictionary().addReaction("H2O+H2O+CO2=H3O+HCO3", 8e-20, null, 1);
		container.getReactionDictionary().addReaction("H3O+HCO3=H2O+H2O+CO2", 8e-20, "CAT", 1);
		RandomGenerator generator = new BoltzmannGenerator(container, temperature);
		final int nPart = 1000;
		container.addRandomParticles(generator, nPart, "H3O");
		container.addRandomParticles(generator, nPart, "HCO3");
		final int nPartCat = 700;
		container.addRandomParticles(generator, nPartCat, "CAT");
		container.run(deltaTime);
	}
	
	public static void challenge() {
		ParticleContainer container = new ParticleContainer(size, size, size, reactionRadius);

        container.getDictionary().addParticle("CO2", massCO2, colorCO2, meanFreeTime);
        container.getDictionary().addParticle("H2O", massH2O, colorH2O, meanFreeTime);
        container.getDictionary().addParticle("AcetylCoA", massAcetylCoA, colorAcetylCoA, meanFreeTime);
        container.getDictionary().addParticle("Oxaloacetate", massOxaloacetate, colorOxaloacetate, meanFreeTime);
        container.getDictionary().addParticle("HSCoA", massHSCoA, colorHSCoA, meanFreeTime);
        container.getDictionary().addParticle("Citrate", massCitrate, colorCitrate, meanFreeTime);
        container.getDictionary().addParticle("CisAconitate", massCisAconitate, colorCisAconitate, meanFreeTime);
        container.getDictionary().addParticle("Isocitrate", massIsocitrate, colorIsocitrate, meanFreeTime);
        container.getDictionary().addParticle("NAD", massNAD, colorNAD, meanFreeTime);
        container.getDictionary().addParticle("NADH", massNADH, colorNADH, meanFreeTime);
        container.getDictionary().addParticle("H", massH, colorH, meanFreeTime);
        container.getDictionary().addParticle("AlphaKetoglutarate", massAlphaKetoglutarate, colorAlphaKetoglutarate, meanFreeTime);
        container.getDictionary().addParticle("SuccinylCoA", massSuccinylCoA, colorSuccinylCoA, meanFreeTime);
        container.getDictionary().addParticle("GDP", massGDP, colorGDP, meanFreeTime);
        container.getDictionary().addParticle("P", massP, colorP, meanFreeTime);
        container.getDictionary().addParticle("GTP", massGTP, colorGTP, meanFreeTime);
        container.getDictionary().addParticle("Succinate", massSuccinate, colorSuccinate, meanFreeTime);
        container.getDictionary().addParticle("Fumarate", massFumarate, colorFumarate, meanFreeTime);
        container.getDictionary().addParticle("FAD", massFAD, colorFAD, meanFreeTime);
        container.getDictionary().addParticle("FADH2", massFADH2, colorFADH2, meanFreeTime);
        container.getDictionary().addParticle("Malate", massMalate, colorMalate, meanFreeTime);


        container.getReactionDictionary().addReaction("AcetylCoA+Oxaloacetate=HSCoA+Citrate", 0, null, 1); //step 1
        container.getReactionDictionary().addReaction("Citrate=H2O+CisAconitate", 0, null, 1); //step 2
        container.getReactionDictionary().addReaction("CisAconitate+H2O=Isocitrate", 0, null, 1); //step 3
        container.getReactionDictionary().addReaction("Isocitrate+NAD=CO2+NADH+H+AlphaKetoglutarate", 0, null, 1);
        container.getReactionDictionary().addReaction("AlphaKetoglutarate+NAD+HSCoA=CO2+NADH+H+SuccinylCoA", 0, null, 1); //step 5
        container.getReactionDictionary().addReaction("SuccinylCoA+GDP+P+H2O=GTP+HSCoA+Succinate", 0, null, 1); //step 6
        container.getReactionDictionary().addReaction("Succinate+FAD=FADH2+Fumarate", 0, null, 1); //step 7
        container.getReactionDictionary().addReaction("Fumarate+H2O=Malate", 0, null, 1); //step 8
        container.getReactionDictionary().addReaction("Malate+NAD=NADH+H+Oxaloacetate", 0, null, 1); //step 9


        RandomGenerator generator = new BoltzmannGenerator(container, temperature);
        final int nParticles = 1000;
        container.addRandomParticles(generator, nParticles, "H2O");
        container.addRandomParticles(generator, nParticles, "AcetylCoA");
        container.addRandomParticles(generator, nParticles, "Oxaloacetate");
        container.addRandomParticles(generator, nParticles, "NAD");
        container.addRandomParticles(generator, nParticles, "GDP");
        container.addRandomParticles(generator, nParticles, "P");
        container.addRandomParticles(generator, nParticles, "FAD");
        container.run(deltaTime);
	}
	
	public static void unitAssessment() {
		ParticleContainer container = new ParticleContainer(size, size, size, reactionRadius);
		
		container.getDictionary().addParticle("H2O", massH2O, colorH2O, meanFreeTime);
		container.getDictionary().addParticle("Mg", massMg, colorMg, meanFreeTime);
		container.getDictionary().addParticle("H", massH, colorH, meanFreeTime);
		container.getDictionary().addParticle("OH", massOH, colorOH, meanFreeTime);
		container.getDictionary().addParticle("Cl", massCl, colorCl, meanFreeTime);
		container.getDictionary().addParticle("MgOH2", massMgOH2, colorMgOH2, meanFreeTime);
		
		container.getReactionDictionary().addReaction("MgOH2=Mg+OH+OH", 0, null, 0.1);
		container.getReactionDictionary().addReaction("OH+H=H2O", 0, null, 1);
		container.getReactionDictionary().addReaction("Mg+OH+OH=MgOH2", 0, null, 0.9);
		
		 RandomGenerator generator = new BoltzmannGenerator(container, temperature);
	     final int nParticles = 1000;
	     container.addRandomParticles(generator, 0, "MgOH2");
	     container.addRandomParticles(generator, 360, "OH");
	     container.addRandomParticles(generator, 2000, "Mg");
	     //container.addRandomParticlesDelay(generator, 360, "H", 30);
	     //container.addRandomParticlesDelay(generator, 360, "H", 80);
	     //container.addRandomParticlesDelay(generator, 1800, "H", 170);
	     //container.addRandomParticlesDelay(generator, 1800, "H", 210);
	     //container.addRandomParticles(generator, nParticles, "OH");
	     container.run(deltaTime);
		
	}

}