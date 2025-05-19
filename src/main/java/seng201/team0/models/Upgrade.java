package seng201.team0.models;

/**
 * Represents an upgrade that can be applied to a car to improve its stats.
 */
public class Upgrade {

    /**
     * The name of the upgrade.
     */
    private final String upgradeName;

    /**
     * The speed boost provided by this upgrade.
     */
    private final int upgradeSpeed;

    /**
     * The handling improvement provided by this upgrade.
     */
    private final int upgradeHandling;

    /**
     * The reliability increase provided by this upgrade.
     */
    private final int upgradeReliability;

    /**
     * The fuel economy enhancement provided by this upgrade.
     */
    private final int upgradeFuelEconomy;

    /**
     * The cost required to purchase this upgrade.
     */
    private final int upgradeCost;

    /**
     * Constructs a new {@link Upgrade} instance
     *
     * @param upgradeName the name of the upgrade (String)
     * @param upgradeSpeed the speed enhancement value (int)
     * @param upgradeHandling the handling enhancement value (int)
     * @param upgradeReliability the reliability enhancement value (int)
     * @param upgradeFuelEconomy the fuel economy enhancement value (int)
     * @param upgradeCost the cost of the upgrade (int)
     */
    public Upgrade(String upgradeName, int upgradeSpeed, int upgradeHandling, int upgradeReliability, int upgradeFuelEconomy, int upgradeCost) {
        this.upgradeName = upgradeName;
        this.upgradeSpeed = upgradeSpeed;
        this.upgradeHandling = upgradeHandling;
        this.upgradeReliability = upgradeReliability;
        this.upgradeFuelEconomy = upgradeFuelEconomy;
        this.upgradeCost = upgradeCost;
    }

    /**
     * Returns the name of the upgrade.
     *
     * @return the name of the upgrade
     */
    public String getUpgradeName() { return upgradeName; }

    /**
     * Returns the speed enhancement provided by the upgrade.
     *
     * @return the speed boost from the upgrade
     */
    public int getUpgradeSpeed() { return upgradeSpeed; }

    /**
     * Returns the handling enhancement provided by the upgrade.
     *
     * @return the handling boost from the upgrade
     */
    public int getUpgradeHandling() { return upgradeHandling; }

    /**
     * Returns the reliability enhancement provided by the upgrade.
     *
     * @return the reliability boost from the upgrade
     */
    public int getUpgradeReliability() { return upgradeReliability; }

    /**
     * Returns the fuel economy enhancement provided by the upgrade.
     *
     * @return the fuel economy boost from the upgrade
     */
    public int getUpgradeFuelEconomy() { return upgradeFuelEconomy; }

    /**
     * Returns the cost of the upgrade.
     *
     * @return the cost of the upgrade
     */
    public int getUpgradeCost() { return upgradeCost; }
}
