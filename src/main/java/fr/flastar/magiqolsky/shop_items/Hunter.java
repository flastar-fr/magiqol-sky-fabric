package fr.flastar.magiqolsky.shop_items;

public enum Hunter implements IShopItems {
    RAW_CHICKEN("minecraft:chicken", .5f),
    FEATHER("minecraft:feather", 20.f),
    RAW_PORKCHOP("minecraft:porkchop", 1.f),
    RAW_BEEF("minecraft:beef", .5f),
    LEATHER("minecraft:leather", 20.f),
    RAW_RABBIT("minecraft:rabbit", .5f),
    RABBIT_HIDE("minecraft:rabbit_hide", 20.f),
    RABBIT_FOOT("minecraft:rabbit_foot", 325.f),
    RAW_MUTTON("minecraft:mutton", .7f),
    ROTTON_FLESH("minecraft:rotten_flesh", 2.f),
    STRING("minecraft:string", .5f),
    SPIDER_EYE("minecraft:spider_eye", 5.f),
    GUNPOWDER("minecraft:gunpowder", 20.f),
    SLIMEBALL("minecraft:slime_ball", 4.f),
    BONE("minecraft:bone", 0.2f),
    ARROW("minecraft:arrow", 1.5f),
    BOW("minecraft:bow", 3.f),
    GLOW_INK_SAC("minecraft:glow_ink_sac", 10.f),
    SOUL_1("nexo:soul1", 1.f),
    SOUL_2("nexo:soul2", 10.f),
    SOUL_3("nexo:soul3", 100.f),
    SOUL_4("nexo:soul4", 1000.f),
    SOUL_5("nexo:soul5", 10000.f),
    PANDA_TEETH("nexo:panda_teeth", 375.f),
    PANDARON_CLAW("nexo:pandaron_claw", 750.f),
    BROKEN_STICK("nexo:broken_stick", 175.f),
    FROG_EYE("nexo:frog_eye", 600.f),
    OCHRE_FROGLIGHT("minecraft:ochre_froglight", 850.f),
    VERDANT_FROGLIGHT("minecraft:verdant_froglight", 850.f),
    PEARLESCENT_FROGLIGHT("minecraft:pearlescent_froglight", 850.f),
    FROGSPAWN_EGG("minecraft:frogspawn", 2.f),
    SOGGY_ROPE("nexo:soggy_rope", 175.f),
    MODLY_CLOTH("nexo:moldy_cloth", 325.f),
    TRIDENT("minecraft:trident", 750.f),
    SHARP_STONE("nexo:sharp_stone", 1000.f),
    INK_SAC("minecraft:ink_sac", 9.f),
    SQUID_TENTACLE("nexo:squid_tentacle", 375.f),
    BREEZE_ROD("minecraft:breeze_rod", 1325.f),
    HEVEA_BRANCH("nexo:hevea_branch", 355.f),
    POISON_STRING("nexo:poison_string", 950.f),
    PRIMAL_SARBACANE("nexo:primal_sarbacane", 315.f),
    PRIMAL_WARRIOR_SPEAR_TIP("nexo:primal_warrior_spear_tip", 250.f),
    PRIMAL_WARRIOR_BELT("nexo:primal_warrior_belt", 155.f),
    SHAMAN_TOTEM_FRAGMENT("nexo:shaman_totem_fragment", 625.f),
    STRONG_FEMUR("nexo:strong_femur", 1750.f),
    RESISTANT_LIANA("nexo:resistant_liana", 1225.f),
    BLACK_PANTHER_SKIN("nexo:black_panther_skin", 2875.f),
    PARROT_FEATHER("nexo:parrot_feather", 3950.f),
    BROKEN_COCONUT("nexo:broken_coconut", 525.f),
    RUBBER_TREE_SAP("nexo:rubber_tree_sap", 625.f),
    THISTLE("nexo:thistle", 40.f),
    POLLEN("nexo:pollen", 20.f),
    BUMBLEBEE_STING("nexo:bumblebee_sting", 2455.f),
    MOOSHROOM_HORN("nexo:mooshroom_horn", 6750.f),
    BURNING_MUSHROOM_FOOT("nexo:burning_mushroom_foot", 2850.f),
    PIRANHA_FIN("nexo:piranha_fin", 3125.f),
    SEDIMENTARY_POWDER("nexo:sedimentary_powder", 725.f),
    GLOW_SQUID_TENTACLE("nexo:glow_squid_tentacle", 2750.f),
    WOODEN_MECHANISM("nexo:wooden_mechanism", 4125.f),
    LOST_LANTERN("nexo:lost_lantern", 1725.f),
    FLAX_THREAD("nexo:flax_thread", 1095.f),
    ZOMBIE_HEAD("minecraft:zombie_head", 550.f),
    SKELETON_SKULL("minecraft:skeleton_skull", 3250.f),
    GHOSTLY_FABRIC("nexo:ghostly_fabric", 2250.f),
    CELESTIAL_TOUCAN_FEATHER("nexo:celestial_toucan_feather", 9550.f),
    NM_TOUCAN_BEAK("nexo:nm_toucan_beak", 5500.f),
    GUARDIAN_SCALE("nexo:guardian_scale", 3250.f),
    WET_SPONGE("minecraft:wet_sponge", 925.f),
    BURNING_POISON("nexo:burning_poison", 5000.f),
    VERDANT_TOUCAN_FEATHER("nexo:verdant_toucan_feather", 10500.f),
    REVENANT_LEAVE("nexo:revenant_leave", 3550.f),
    VERDANT_APPLE("nexo:verdant_apple", 1750.f),
    ARTIFICIAL_HEART("nexo:artificial_heart", 5125.f),
    TWISTED_NAIL("nexo:twisted_nail", 1625.f),
    ANTIQUE_SHOULDER_PAD("nexo:antique_shoulder_pad", 1550.f),
    NAUTILUS_SHELL("minecraft:nautilus_shell", 11500.f),
    NM_ALLIGATOR_SCALE("nexo:nm_alligator_scale", 8215.f),
    ALLIGATOR_TAIL("nexo:alligator_tail", 21525.f),
    MIRED_TUNIC("nexo:mired_tunic", 9725.f),
    SACRED_MUD("nexo:sacred_mud", 19500.f),
    WITCH_CRISTAL("nexo:witch_cristal", 8505.f),
    CURSED_SPOON("nexo:cursed_spoon", 22750.f),
    SHADOW_TOUCAN_FEATHER("nexo:shadow_toucan_feather", 13250.f),
    PRIMAL_ZOMBIE_SARBACANE("nexo:primal_zombie_sarbacane", 8535.f),
    PRIMAL_WARRIOR_ZOMBIE_JAW("nexo:primal_warrior_zombie_jaw", 9205.f),
    PRIMAL_WARRIOR_ZOMBIE_BELT("nexo:primal_zombie_warrior_belt", 4535.f),
    ANCIENT_MAGIC_POWDER("nexo:ancient_magic_powder", 3255.f),
    NECROMANCER_BROKEN_STAFF("nexo:necromancer_broken_staff", 34525.f);

    private final String id;
    private final float price;

    Hunter(String id, float price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
