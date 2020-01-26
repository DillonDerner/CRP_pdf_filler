package twenty_eighteen;

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

    private static final String BASE_CRP         = "C:/dev/fillable-pdf/pdf_filler/base_crp/2018_base_crp.pdf";
    private static final String TENANT_CRP_DATA  = "C:/dev/fillable-pdf/pdf_filler/csv_data/2018_tenant_list.csv";
    private static String year = "2018";
    private static String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    private static BaseFont courier = FontFactory.getFont("Courier").getBaseFont();

    public static void main(String[] args) {
        System.out.println("Hello World!");

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\\|";

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

        String name = crpLine.getRenter_name().replace(' ', '_');

        String outpath = String.format("C:/dev/fillable-pdf/pdf_filler/output_crps/%s/%s_%s_%s_CRP.pdf", year, year, crpLine.getCompany(), name);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outpath));
        AcroFields fields = stamper.getAcroFields();


        // String[] states = fields.getAppearanceStates("cx_is_mobile_home");
        // fields.renameField("untitled1", "renter_name");
        // To check the box set value to "On"
        // Checkboxes
        fields.setField("cx_is_mobile_home", isOn(crpLine.getCx_is_mobile_home()));
        fields.setField("cx_adult_foster_care", ""); // not working
        fields.setField("cx_is_married_couple", isOn(crpLine.getCx_is_married_couple()));
        fields.setField("cx_is_intermediate_care_facility", ""); // not used
        fields.setField("cx_reduced_for_caretaker", ""); // not used
        fields.setField("cx_is_nursing_home", ""); // not used
        fields.setField("cx_government_agency_helped", isOn(crpLine.getCx_government_agency_helped()));
        fields.setField("cx_assisted_living", ""); // not used

        // Unknown Text Fields
        fields.setField("not_used1", "");
        fields.setField("not_used2", "");

        // Text Fields Font and Pt
        fields = setFont(fields, "renter_name");
        fields = setFont(fields, "rent_paid");
        fields = setFont(fields, "date");
        fields = setFont(fields, "rental_start");
        fields = setFont(fields, "business_phone");
        fields = setFont(fields, "county");
        fields = setFont(fields, "rent_paid_times_percentage");
        fields = setFont(fields, "properties_total_units");
        fields = setFont(fields, "rental_end");
        fields = setFont(fields, "property_parcel_number");
        fields = setFont(fields, "total_months_rented");
        fields = setFont(fields, "unit_address_line1");
        fields = setFont(fields, "unit_address_line2");
        fields = setFont(fields, "owners_name");
        fields = setFont(fields, "owners_address");
        fields = setFont(fields, "rent_reduction");
        fields = setFont(fields, "housing_support");
        fields = setFont(fields, "medical_assistance");
        fields = setFont(fields, "number_of_adults");
        fields = setFont(fields, "signature");

        // Text Fields Content
        fields.setField("renter_name",                crpLine.getRenter_name());
        fields.setField("unit_address_line1",         crpLine.getUnit_address());
        fields.setField("unit_address_line2",         getAddressLine2(crpLine.getCity(), crpLine.getState(), crpLine.getZipcode()));
        fields.setField("rent_paid",                  crpLine.getRent_paid());
        fields.setField("rental_start",               crpLine.getRental_start());
        fields.setField("rental_end",                 crpLine.getRental_end());
        fields.setField("county",                     crpLine.getCounty());
        fields.setField("rent_paid_times_percentage", getRentalPercentage(crpLine.getRent_paid()));
        fields.setField("properties_total_units",     crpLine.getProperties_total_units());
        fields.setField("property_parcel_number",     crpLine.getProperty_parcel_number());
        fields.setField("total_months_rented",        getTotalMonthsRented(crpLine.getRental_start(), crpLine.getRental_end()));
        fields.setField("number_of_adults",           crpLine.getNumber_of_adults());
        fields.setField("rent_reduction",             crpLine.getRent_reduction());
        fields.setField("housing_support",            crpLine.getHousing_support());
        fields.setField("medical_assistance",         crpLine.getMedical_assistance());
        fields.setField("owners_name",                crpLine.getOwners_name());
        fields.setField("owners_address",             crpLine.getOwners_address());
        fields.setField("business_phone",             crpLine.getBusiness_phone());
        fields.setField("date",                       date);
        fields.setField("signature",                  crpLine.getSignature());

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
