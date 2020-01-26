package twenty_nineteen;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    private static final String BASE_CRP         = "C:/dev/fillable-pdf/pdf_filler/base_crp/2019_base_crp.pdf";
    private static final String TENANT_CRP_DATA  = "C:/dev/fillable-pdf/pdf_filler/csv_data/2019_example_list.csv";
    private static String year = "2019";
    private static String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    private static BaseFont courier = FontFactory.getFont("Courier").getBaseFont();

    public static void main(String[] args) {
        System.out.println("Hello World!");

        BufferedReader br = null;
        String line = "";
//        String cvsSplitBy = "\\|";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(TENANT_CRP_DATA));
            br.readLine(); // this should skip the header row
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] tenant_crp_data = line.split(cvsSplitBy);
                CrpLine crpLine = new CrpLine(tenant_crp_data);
                manipulatePdf(crpLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void manipulatePdf(CrpLine crpLine) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(BASE_CRP);

        String crp_tenant_name = crpLine.getRenter_fname() + "_" + crpLine.getRenter_lname();

        String outpath = String.format("C:/dev/fillable-pdf/pdf_filler/output_crps/%s/%s_%s_%s_CRP.pdf", year, year, crpLine.getCompany(), crp_tenant_name);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outpath));
        AcroFields fields = stamper.getAcroFields();


//        USED FOR NUMBERING FIELDS!

//        Map<String, AcroFields.Item> asdf = fields.getFields();
//
//        int counter = 0;
//        for (String field :  asdf.keySet()) {
//            fields.setField(field, String.valueOf(counter));
//            System.out.println(field + ":" + String.valueOf(counter));
//            counter++;
//        }


        // To check the box set value to "On"
        // Checkboxes
        fields.setField("MobileHome", isOn(crpLine.getCx_is_mobile_home()));
        fields.setField("MobileHomeLot", isOn(crpLine.getCx_is_mobile_home()));
        fields.setField("AdultFosterCare", "");             // not used
        fields.setField("IntermediateCareFacility", "");    // not used
        fields.setField("NursingHome", "");                 // not used
        fields.setField("AssistedLiving", "");              // not used


        if (crpLine.getMedical_assistance().isEmpty()){
            fields.setField("medicaidno", "On");
        } else {
            fields.setField("medicaidyes", "On");
            fields = setFont(fields, "A");
            fields.setField("A", crpLine.getMedical_assistance());
        }

        if (crpLine.getHousing_support().isEmpty()){
            fields.setField("housingsupportno", "On");
        } else {
            fields.setField("housingsupportyes", "On");
            fields = setFont(fields, "B");
            fields.setField("B", crpLine.getHousing_support());
        }


        // Text Fields Font and Pt
        fields = setFont(fields, "DaytimePhone");
        fields = setFont(fields, "renterstate");
        fields = setFont(fields, "toMMDDYYYY");
        fields = setFont(fields, "ZIPCode");
        fields = setFont(fields, "rentercity");
        fields = setFont(fields, "propertyownerDaytimePhone");
        fields = setFont(fields, "propertyidorparcel");
        fields = setFont(fields, "numberofunits");
        fields = setFont(fields, "RentedfromMMDDYYYY");
        fields = setFont(fields, "renterzip");
        fields = setFont(fields, "Renterlastname");
        fields = setFont(fields, "TotalMonthsRented");
        fields = setFont(fields, "Renterfirstnameandinitial");
        fields = setFont(fields, "Signaturedate");
        fields = setFont(fields, "City");
        fields = setFont(fields, "PropertyOwnername");
        fields = setFont(fields, "PropertyOwnerAddress");
        fields = setFont(fields, "ECN");
        fields = setFont(fields, "1");
        fields = setFont(fields, "unit");
        fields = setFont(fields, "2");
        fields = setFont(fields, "3");
        fields = setFont(fields, "unitaddress");
        fields = setFont(fields, "rentercounty");
        fields = setFont(fields, "State");
        fields = setFont(fields, "NumberofAdultsLivinginUnit");
        fields = setFont(fields, "MangingAgentNameIfApplicablepleaseprint");


        // Text Fields Content
        fields.setField("DaytimePhone",              crpLine.getBusiness_phone());
        fields.setField("renterstate",               "MN");
        fields.setField("toMMDDYYYY",                crpLine.getRental_end());
        fields.setField("ZIPCode",                   "55388");
        fields.setField("rentercity",                crpLine.getState());
        fields.setField("propertyownerDaytimePhone", crpLine.getBusiness_phone());
        fields.setField("propertyidorparcel",        crpLine.getProperty_parcel_number());
        fields.setField("numberofunits",             crpLine.getProperties_total_units());
        fields.setField("RentedfromMMDDYYYY",        crpLine.getRental_start());
        fields.setField("renterzip",                 crpLine.getZipcode());
        fields.setField("Renterlastname",            crpLine.getRenter_lname());
        fields.setField("TotalMonthsRented",         getTotalMonthsRented(crpLine.getRental_start(), crpLine.getRental_end()));
        fields.setField("Renterfirstnameandinitial", crpLine.getRenter_fname());
        fields.setField("Signaturedate",             date);
        fields.setField("City",                      crpLine.getCity());
        fields.setField("PropertyOwnername",         crpLine.getOwners_name());
        fields.setField("PropertyOwnerAddress",      crpLine.getOwners_address());
        // The Electronic Certification Number (ECN) is generated when creating CRPs in e-Services. If you do not use e-Services, leave this field blank.
        fields.setField("ECN",                       "");
        fields.setField("1",                         crpLine.getRent_paid());  // Renter’s share of rent paid
        fields.setField("2",                         "0");               // Caretaker rent reduction
        fields.setField("3",                         crpLine.getRent_paid());  // Total rent (Add lines 1 and 2
        fields.setField("unit",                      crpLine.getAppartment_number());
        fields.setField("unitaddress",               crpLine.getUnit_address());
        fields.setField("rentercounty",              crpLine.getCounty());
        fields.setField("State",                     crpLine.getState());
        fields.setField("NumberofAdultsLivinginUnit",crpLine.getNumber_of_adults());
        fields.setField("MangingAgentNameIfApplicablepleaseprint",crpLine.getSignature());

        // Write out
        stamper.setFormFlattening(true); // this makes all fields un editable
        stamper.close();
        reader.close();
    }

    private static String isOn(String hasValue) {
        if (hasValue.isEmpty()) {
            return "";
        } else {
            return "On";
        }
    }

    private static AcroFields setFont(AcroFields fields, String fieldName) {
        fields.setFieldProperty(fieldName, "textfont", courier, null);
        fields.setFieldProperty(fieldName, "textsize", new Float(12), null);
        return fields;
    }

    private static String getAddressLine2(String city, String state, String zip) {
        return String.format("%s, %s %s", city, state, zip);
    }

    private static String getRentalPercentage(String amountPaid) {
        double percentage = 0.17;
        double intAmountPaid = Double.parseDouble(amountPaid);
        double rentalPercentage = intAmountPaid * percentage;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(rentalPercentage);
    }

    private static String getTotalMonthsRented(String start, String end) {
        int startMonth = Integer.parseInt(start.split("/")[0]);
        int endMonth = Integer.parseInt(end.split("/")[0]);
        return Integer.toString(endMonth - startMonth + 1);
    }

}
