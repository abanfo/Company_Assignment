package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculationClass {
	private static final double januaryInitialTemp = 3;
	private static final double februaryInitialTemp = 3.1;
	private static final double marchInitialTemp = 5.7;
	private static final double aprilInitialTemp = 8.2;
	private static final double mayInitialTemp = 12.5;
	private static final double juneInitialTemp = 15;
	private static final double julyInitialTemp = 17.1;
	private static final double augustInitialTemp = 17.1;
	private static final double septemberInitialTemp = 14.3;
	private static final double octoberInitialTemp = 10.6;
	private static final double novemberInitialTemp = 6.6;
	private static final double decemberInitialTemp = 4.3;

	private static final double floraFormulaOptimumTempGrass = 19;
	private static final double floraFormulaModifier = 5.5;
	private static final double maxGrowthRateGrass = 60;
	private static final double areaSize = 5600;

	private static final double amtGrassEatenHorseDay = 10.8;
	private static final double amtGrassEatenCattleDay = 7.2;

	private static final double growthRateHorse = 0.2;
	private static final double growthRateCattle = 0.17;
	private static final double competitionCoeffCattle = 0.63;
	private static final double competitionCoeffHorse = 1.6;

	private static final int amtYears = 40;
	private static final int months = 12;

	private List<Double> monthlyTemperature;
	private ArrayList<Double> monthlyTemperatureForWholePeriod;
	private ArrayList<Double> populationHorsesWholePeriod;
	private ArrayList<Double> populationCattleWholePeriod;

	public CalculationClass() {

		this.monthlyTemperature = Arrays.asList(januaryInitialTemp, februaryInitialTemp, marchInitialTemp,
				aprilInitialTemp, mayInitialTemp, juneInitialTemp, julyInitialTemp, augustInitialTemp,
				septemberInitialTemp, octoberInitialTemp, novemberInitialTemp, decemberInitialTemp);
	}

	public void calculate(int initHorse, int initCattle, double tempChange) {
		setTempByOneValue(tempChange);
		mainCalculation(initHorse, initCattle);
	}

	public void calculate(int initHorse, int initCattle, ArrayList<Double> tempChange) throws Exception {
		setTempByTenValues(tempChange);
		mainCalculation(initHorse, initCattle);
	}

	private void mainCalculation(int initHorse, int initCattle) {
		this.populationHorsesWholePeriod = new ArrayList<Double>();
		this.populationCattleWholePeriod = new ArrayList<Double>();
		double horsePopulation = initHorse;
		double cattlePopulation = initCattle;
		for (double monthlyTemp : monthlyTemperatureForWholePeriod) {
			double growthPotential = calculateGrowthpotential(monthlyTemp);
			double bioMass = growthPotential * CalculationClass.maxGrowthRateGrass * CalculationClass.areaSize;
			double carryingCapacityHorses = bioMass / CalculationClass.amtGrassEatenHorseDay;
			double carryingCapacityCattle = bioMass / CalculationClass.amtGrassEatenCattleDay;

			// parking value for horse population so we can use it to calculate
			// the cattle
			double horsePlaceholder = horsePopulation;

			horsePopulation = calculatePopulationHorses(carryingCapacityHorses, horsePopulation, cattlePopulation);
			System.out.println("population horses: " + horsePopulation);	
			cattlePopulation = calculatePopulationCattle(carryingCapacityCattle, cattlePopulation, horsePlaceholder);
			this.populationHorsesWholePeriod.add(horsePopulation);
			this.populationCattleWholePeriod.add(cattlePopulation);
		}
		
	}

	private void setTempByOneValue(double tempChange) {
		this.monthlyTemperatureForWholePeriod = new ArrayList<Double>();
		double currentIncrease = 0;
		double yearlyChange = tempChange / CalculationClass.amtYears;
		for (int i = 0; i < CalculationClass.amtYears; i++) {
			// iterating over this.amtYears years
			currentIncrease = createmonthlyTemperatureForWholePeriodList(yearlyChange, currentIncrease);
		}
	}

	private void setTempByTenValues(ArrayList<Double> tempChange) throws Exception {
		this.monthlyTemperatureForWholePeriod = new ArrayList<Double>();
		double currentIncrease = 0;
		if (tempChange.size() != CalculationClass.amtYears) {
			throw new Exception("the array needs to be of length" +CalculationClass.amtYears );
		}
		for (double tempChangeInYear : tempChange) {
			// iterating over our array of size this.amtYears which is this.amtYears years
			currentIncrease = createmonthlyTemperatureForWholePeriodList(tempChangeInYear, currentIncrease);
		}
	}

	private double calculatePopulationHorses(double carryingCapacityHorses, double horsePopulation,
			double cattlePopulation) {
		double growthRatePerMonth = CalculationClass.growthRateHorse / CalculationClass.months;

		double horseGrowthperMonth = growthRatePerMonth * horsePopulation
				* (1 - (horsePopulation / carryingCapacityHorses)
						- (CalculationClass.competitionCoeffCattle * cattlePopulation / carryingCapacityHorses));
		return horsePopulation + horseGrowthperMonth;
	}

	private double calculatePopulationCattle(double carryingCapacityCattle, double cattlePopulation,
			double horsePopulation) {
		double growthRatePerMonth = CalculationClass.growthRateCattle / CalculationClass.months;

		double horseGrowthperMonth = growthRatePerMonth * cattlePopulation
				* (1 - (cattlePopulation / carryingCapacityCattle)
						- (CalculationClass.competitionCoeffHorse * horsePopulation / carryingCapacityCattle));
		return cattlePopulation + horseGrowthperMonth;
	}

	private double calculateGrowthpotential(double monthlyTemp) {
		double e = java.lang.Math.E;
		// pulling the formula apart a bit here for easier readability
		double formulaInnerBracket = (monthlyTemp - CalculationClass.floraFormulaOptimumTempGrass)
				/ floraFormulaModifier;
		double formulAboveE = -0.5 * Math.pow(formulaInnerBracket, 2);
		double growthPotential = Math.pow(e, formulAboveE);
		return growthPotential;
	}

	private double createmonthlyTemperatureForWholePeriodList(double tempChangeInYear, double currentIncrease) {

		double thisYearsmonthlyChange = tempChangeInYear / CalculationClass.months;
		// creating a linear increase in temperature --we smear the temperature
		// over the whole year
		for (int i = 0; i < CalculationClass.months; i++) {
			currentIncrease += thisYearsmonthlyChange;
			// System.out.println(monthlyTemperature.get(i) + currentIncrease);

			this.monthlyTemperatureForWholePeriod.add(monthlyTemperature.get(i) + currentIncrease);
		}
		return currentIncrease;
	}

	public ArrayList<Double> getPopulationHorsesWholePeriod() {
		return populationHorsesWholePeriod;
	}

	public ArrayList<Double> getPopulationCattleWholePeriod() {
		return populationCattleWholePeriod;
	}

}
