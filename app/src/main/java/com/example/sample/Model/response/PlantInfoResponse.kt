package com.example.sample.Model.response


import com.google.gson.annotations.SerializedName

data class PlantInfoResponse(
    @SerializedName("Contact us Info")
    val contactUsInfo: List<ContactUsInfo>,
    @SerializedName("District Info")
    val districtInfo: List<DistrictInfo>,
    @SerializedName("Form factor Info")
    val formFactorInfo: ArrayList<FormFactorInfo>,
    @SerializedName("GPS Info")
    val gPSInfo: List<GPSInfo>,
    @SerializedName("Glossary Info")
    val glossaryInfo: List<GlossaryInfo>,
    @SerializedName("Intercrops Info")
    val intercropsInfo: ArrayList<IntercropsInfo>,
    @SerializedName("Models Info")
    val modelsInfo: ArrayList<ModelsInfo>,
    @SerializedName("Rainfall Info")
    val rainfallInfo: List<RainfallInfo>,
    @SerializedName("Reference Info")
    val referenceInfo: List<ReferenceInfo>,
    @SerializedName("Scheme Info")
    val schemeInfo: List<SchemeInfo>,
    @SerializedName("Soil Info")
    val soilInfo: List<SoilInfo>,
    @SerializedName("Terrain Info")
    val terrainInfo: List<TerrainInfo>,
    @SerializedName("Treecount Info")
    val treecountInfo: Int,
    @SerializedName("Treenames Info")
    val treenamesInfo: ArrayList<TreenamesInfo>,
    @SerializedName("Treetypes Info")
    val treetypesInfo: List<TreetypesInfo>,
    @SerializedName("Zones Info")
    val zonesInfo: List<ZonesInfo>
) {
    data class ContactUsInfo(
        @SerializedName("Address")
        val address: String,
        @SerializedName("AddressTamil")
        val addressTamil: String,
        @SerializedName("Direction")
        val direction: String,
        @SerializedName("District")
        val district: String,
        @SerializedName("DistrictTamil")
        val districtTamil: String,
        @SerializedName("Email")
        val email: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("PhoneNo")
        val phoneNo: String,
        @SerializedName("serverId")
        val serverId: String
    )

    data class DistrictInfo(
        @SerializedName("District")
        val district: String,
        @SerializedName("DistrictImage")
        val districtImage: String,
        @SerializedName("DistrictTamil")
        val districtTamil: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("treetypes")
        val treetypes: String,
        @SerializedName("treetypesTamil")
        val treetypesTamil: String
    )

    data class FormFactorInfo(
        @SerializedName("serverId")
        val serverId: String,
        @SerializedName("tree")
        val tree: String,
        @SerializedName("value")
        val value: String
    )

    data class GPSInfo(
        @SerializedName("District")
        val district: String,
        @SerializedName("Lattitude")
        val lattitude: String,
        @SerializedName("Longitude")
        val longitude: String,
        @SerializedName("Office")
        val office: String
    )

    data class GlossaryInfo(
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Meaning")
        val meaning: String,
        @SerializedName("MeaningTamil")
        val meaningTamil: String,
        @SerializedName("Word")
        val word: String,
        @SerializedName("WordTamil")
        val wordTamil: String,
        @SerializedName("highlight")
        var highlight: Boolean
    )

    data class IntercropsInfo(
        @SerializedName("Description")
        val description: String,
        @SerializedName("Description_Tamil")
        val descriptionTamil: String,
        @SerializedName("Image")
        val image: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("MaincropImage")
        val maincropImage: String,
        @SerializedName("Treename")
        val treename: String,
        @SerializedName("TreenameTamil")
        val treenameTamil: String,
        @SerializedName("isVisible")
        var isVisible: Boolean = false
    )

    data class ModelsInfo(
        @SerializedName("Description")
        val description: String,
        @SerializedName("Description_Tamil")
        val descriptionTamil: String,
        @SerializedName("District")
        val district: String,
        @SerializedName("District_tamil")
        val districtTamil: String,
        @SerializedName("Image")
        val image: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Zone")
        val zone: String,
        @SerializedName("Zone_Tamil")
        val zoneTamil: String,
        @SerializedName("isVisible")
        var isVisible: Boolean = false
    )

    data class RainfallInfo(
        @SerializedName("District")
        val district: String,
        @SerializedName("District_tamil")
        val districtTamil: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Rainfall")
        val rainfall: String,
        @SerializedName("RainfallTamil")
        val rainfallTamil: String
    )

    data class ReferenceInfo(
        @SerializedName("reference")
        val reference: String,
        @SerializedName("reference_tamil")
        val referenceTamil: String,
        @SerializedName("serverId")
        val serverId: String
    )

    data class SchemeInfo(
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Scheme1")
        val scheme1: String,
        @SerializedName("Scheme1_Tamil")
        val scheme1Tamil: String,
        @SerializedName("Scheme2")
        val scheme2: String,
        @SerializedName("Scheme2_Tamil")
        val scheme2Tamil: String,
        @SerializedName("Scheme3")
        val scheme3: String,
        @SerializedName("Scheme3_Tamil")
        val scheme3Tamil: String,
        @SerializedName("Scheme4")
        val scheme4: String,
        @SerializedName("Scheme4_Tamil")
        val scheme4Tamil: String,
        @SerializedName("SchemeTitle")
        val schemeTitle: String,
        @SerializedName("SchemeTitleTamil")
        val schemeTitleTamil: String,
        @SerializedName("isVisible")
        var isVisible: Boolean = false
    )

    data class SoilInfo(
        @SerializedName("District")
        val district: String,
        @SerializedName("District_tamil")
        val districtTamil: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Soil")
        val soil: String,
        @SerializedName("SoilTamil")
        val soilTamil: String
    )

    data class TerrainInfo(
        @SerializedName("District")
        val district: String,
        @SerializedName("District_tamil")
        val districtTamil: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Terrain")
        val terrain: String,
        @SerializedName("TerrainTamil")
        val terrainTamil: String
    )

    data class TreenamesInfo(
        @SerializedName("Altitude")
        val altitude: String,
        @SerializedName("Altitude_Tamil")
        val altitudeTamil: String,
        @SerializedName("artificial_regeneration")
        val artificialRegeneration: String,
        @SerializedName("artificial_regeneration_tamil")
        val artificialRegenerationTamil: String,
        @SerializedName("Carbon_Stock")
        val carbonStock: String,
        @SerializedName("Carbon_Stock_Tamil")
        val carbonStockTamil: String,
        @SerializedName("Care_Disease")
        val careDisease: String,
        @SerializedName("Care_Disease_Tamil")
        val careDiseaseTamil: String,
        @SerializedName("common_key")
        val commonKey: String,
        @SerializedName("Conservation")
        val conservation: String,
        @SerializedName("Conservation_Tamil")
        val conservationTamil: String,
        @SerializedName("Cultivation")
        val cultivation: String,
        @SerializedName("Cultivation_Tamil")
        val cultivationTamil: String,
        @SerializedName("Distribution")
        val distribution: String,
        @SerializedName("Distribution_Tamil")
        val distributionTamil: String,
        @SerializedName("District")
        val district: String,
        @SerializedName("DistrictTamil")
        val districtTamil: String,
        @SerializedName("Edibility")
        val edibility: String,
        @SerializedName("Edibility_Tamil")
        val edibilityTamil: String,
        @SerializedName("General_Info")
        val generalInfo: String,
        @SerializedName("General_Info_Tamil")
        val generalInfoTamil: String,
        @SerializedName("Growth")
        val growth: String,
        @SerializedName("Growth_Tamil")
        val growthTamil: String,
        @SerializedName("Habit")
        val habit: String,
        @SerializedName("Habit_Tamil")
        val habitTamil: String,
        @SerializedName("Habitat")
        val habitat: String,
        @SerializedName("Habitat_Tamil")
        val habitatTamil: String,
        @SerializedName("Height")
        val height: String,
        @SerializedName("Height_Tamil")
        val heightTamil: String,
        @SerializedName("Images")
        val images: Images,
        @SerializedName("Intercrops")
        val intercrops: String,
        @SerializedName("Intercrops_Tamil")
        val intercropsTamil: String,
        @SerializedName("Irrigation")
        val irrigation: String,
        @SerializedName("Irrigation_Tamil")
        val irrigationTamil: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("Majar_Uses")
        val majarUses: String,
        @SerializedName("Majar_Uses_Tamil")
        val majarUsesTamil: String,
        @SerializedName("Market_Details")
        val marketDetails: String,
        @SerializedName("Market_Details_Tamil")
        val marketDetailsTamil: String,
        @SerializedName("Max_Temperature")
        val maxTemperature: String,
        @SerializedName("Max_Temperature_Tamil")
        val maxTemperatureTamil: String,
        @SerializedName("Medicinal")
        val medicinal: String,
        @SerializedName("Medicinal_Tamil")
        val medicinalTamil: String,
        @SerializedName("Min_Temperature")
        val minTemperature: String,
        @SerializedName("Min_Temperature_Tamil")
        val minTemperatureTamil: String,
        @SerializedName("nursery_technique")
        val nurseryTechnique: String,
        @SerializedName("nursery_technique_tamil")
        val nurseryTechniqueTamil: String,
        @SerializedName("Other_Details")
        val otherDetails: String,
        @SerializedName("Other_Details_Tamil")
        val otherDetailsTamil: String,
        @SerializedName("Other_Rating")
        val otherRating: String,
        @SerializedName("Other_Rating_Tamil")
        val otherRatingTamil: String,
        @SerializedName("Other_Uses")
        val otherUses: String,
        @SerializedName("Other_Uses_Tamil")
        val otherUsesTamil: String,
        @SerializedName("Plantation_Technique")
        val plantationTechnique: String,
        @SerializedName("Plantation_Technique_Tamil")
        val plantationTechniqueTamil: String,
        @SerializedName("Propagation")
        val propagation: String,
        @SerializedName("Propagation_Tamil")
        val propagationTamil: String,
        @SerializedName("RainFall")
        val rainFall: String,
        @SerializedName("RainFallTamil")
        val rainFallTamil: String,
        @SerializedName("Rainfall")
        val rainfall: String,
        @SerializedName("Rainfall_Tamil")
        val rainfallTamil: String,
        @SerializedName("Recommended_Harvest")
        val recommendedHarvest: String,
        @SerializedName("Recommended_Harvest_Tamil")
        val recommendedHarvestTamil: String,
        @SerializedName("References")
        val references: String,
        @SerializedName("References_Tamil")
        val referencesTamil: String,
        @SerializedName("ScientificName")
        val scientificName: String,
        @SerializedName("ScientificTamil")
        val scientificTamil: String,
        @SerializedName("seed_collection")
        val seedCollection: String,
        @SerializedName("seed_collection_tamil")
        val seedCollectionTamil: String,
        @SerializedName("seed_treatment")
        val seedTreatment: String,
        @SerializedName("seed_treatment_tamil")
        val seedTreatmentTamil: String,
        @SerializedName("Soil")
        val soil: String,
        @SerializedName("Soil_PH")
        val soilPH: String,
        @SerializedName("Soil_PH_Tamil")
        val soilPHTamil: String,
        @SerializedName("Soil_Tamil")
        val soil_Tamil: String,
        @SerializedName("SoilTamil")
        val soilTamil: String,
        @SerializedName("SoilType")
        val soilType: String,
        @SerializedName("Terrain")
        val terrain: String,
        @SerializedName("TerrainType")
        val terrainType: String,
        @SerializedName("TerrainType_Tamil")
        val terrainType_Tamil: String,
        @SerializedName("TerrainTypeTamil")
        val terrainTypeTamil: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("Tree_Char")
        val treeChar: String,
        @SerializedName("Tree_Char_Tamil")
        val treeCharTamil: String,
        @SerializedName("TreeName")
        val treeName: String,
        @SerializedName("TreeNameTamil")
        val treeNameTamil: String,
        @SerializedName("TreeType")
        val treeType: String,
        @SerializedName("TreeTypeTamil")
        val treeTypeTamil: String,
        @SerializedName("Yield")
        val yield: String,
        @SerializedName("Yield_Tamil")
        val yieldTamil: String,
        @SerializedName("highlightScientificName")
        var highlightScientificName: Boolean = false
    ) {
        data class Images(
            @SerializedName("code")
            val code: String,
            @SerializedName("message")
            val message: String,
            @SerializedName("result")
            val result: List<Result>,
            @SerializedName("status")
            val status: String
        ) {
            data class Result(
                @SerializedName("Image")
                val image: String
            )
        }
    }

    data class TreetypesInfo(
        @SerializedName("Image")
        val image: String,
        @SerializedName("LastUpdate")
        val lastUpdate: String,
        @SerializedName("TreeType")
        val treeType: String,
        @SerializedName("TreeTypeTamil")
        val treeTypeTamil: String
    )

    data class ZonesInfo(
        @SerializedName("district")
        val district: String,
        @SerializedName("district_tamil")
        val districtTamil: String,
        @SerializedName("zone")
        val zone: String,
        @SerializedName("zone_tamil")
        val zoneTamil: String,
        @SerializedName("isVisible")
        val isVisible: Boolean
    )
}