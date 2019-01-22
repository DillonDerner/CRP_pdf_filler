public class CrpLine {

    private String renter_name;
    private String spouse;
    private String unit_address;
    private String appartment_number;
    private String city;
    private String state;
    private String zipcode;
    private String property_parcel_number;
    private String properties_total_units;
    private String county;
    private String rental_start;
    private String rental_end;
    private String number_of_adults;
    private String cx_is_married_couple;
    private String rent_paid;
    private String medical_assistance;
    private String housing_support;
    private String cx_is_mobile_home;
    private String cx_government_agency_helped;
    private String rent_reduction;
    private String owners_name;
    private String owners_address;
    private String business_phone;
    private String signature;
    private String company;


    public CrpLine(String[] csvLine) {
        this.renter_name = csvLine[0];
        this.spouse = csvLine[1];
        this.unit_address = csvLine[2];
        this.appartment_number = csvLine[3];
        this.city = csvLine[4];
        this.state = csvLine[5];
        this.zipcode = csvLine[6];
        this.property_parcel_number = csvLine[7];
        this.properties_total_units = csvLine[8];
        this.county = csvLine[9];
        this.rental_start = csvLine[10];
        this.rental_end = csvLine[11];
        this.number_of_adults = csvLine[12];
        this.cx_is_married_couple = csvLine[13];
        this.rent_paid = csvLine[14];
        this.medical_assistance = csvLine[15];
        this.housing_support = csvLine[16];
        this.cx_is_mobile_home = csvLine[17];
        this.cx_government_agency_helped = csvLine[18];
        this.rent_reduction = csvLine[19];
        this.owners_name = csvLine[20];
        this.owners_address = csvLine[21];
        this.business_phone = csvLine[22];
        this.signature = csvLine[23];
        this.company = csvLine[24];
    }

    public String getRenter_name() {
        return renter_name;
    }

    public void setRenter_name(String renter_name) {
        this.renter_name = renter_name;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getUnit_address() {
        return unit_address;
    }

    public void setUnit_address(String unit_address) {
        this.unit_address = unit_address;
    }

    public String getAppartment_number() {
        return appartment_number;
    }

    public void setAppartment_number(String appartment_number) {
        this.appartment_number = appartment_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getProperty_parcel_number() {
        return property_parcel_number;
    }

    public void setProperty_parcel_number(String property_parcel_number) {
        this.property_parcel_number = property_parcel_number;
    }

    public String getProperties_total_units() {
        return properties_total_units;
    }

    public void setProperties_total_units(String properties_total_units) {
        this.properties_total_units = properties_total_units;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRental_start() {
        return rental_start;
    }

    public void setRental_start(String rental_start) {
        this.rental_start = rental_start;
    }

    public String getRental_end() {
        return rental_end;
    }

    public void setRental_end(String rental_end) {
        this.rental_end = rental_end;
    }

    public String getNumber_of_adults() {
        return number_of_adults;
    }

    public void setNumber_of_adults(String number_of_adults) {
        this.number_of_adults = number_of_adults;
    }

    public String getCx_is_married_couple() {
        return cx_is_married_couple;
    }

    public void setCx_is_married_couple(String cx_is_married_couple) {
        this.cx_is_married_couple = cx_is_married_couple;
    }

    public String getRent_paid() {
        return rent_paid;
    }

    public void setRent_paid(String rent_paid) {
        this.rent_paid = rent_paid;
    }

    public String getMedical_assistance() {
        return medical_assistance;
    }

    public void setMedical_assistance(String medical_assistance) {
        this.medical_assistance = medical_assistance;
    }

    public String getHousing_support() {
        return housing_support;
    }

    public void setHousing_support(String housing_support) {
        this.housing_support = housing_support;
    }

    public String getCx_is_mobile_home() {
        return cx_is_mobile_home;
    }

    public void setCx_is_mobile_home(String cx_is_mobile_home) {
        this.cx_is_mobile_home = cx_is_mobile_home;
    }

    public String getCx_government_agency_helped() {
        return cx_government_agency_helped;
    }

    public void setCx_government_agency_helped(String cx_government_agency_helped) {
        this.cx_government_agency_helped = cx_government_agency_helped;
    }

    public String getRent_reduction() {
        return rent_reduction;
    }

    public void setRent_reduction(String rent_reduction) {
        this.rent_reduction = rent_reduction;
    }

    public String getOwners_name() {
        return owners_name;
    }

    public void setOwners_name(String owners_name) {
        this.owners_name = owners_name;
    }

    public String getOwners_address() {
        return owners_address;
    }

    public void setOwners_address(String owners_address) {
        this.owners_address = owners_address;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
