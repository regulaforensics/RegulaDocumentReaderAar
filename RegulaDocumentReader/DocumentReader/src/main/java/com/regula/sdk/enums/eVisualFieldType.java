package com.regula.sdk.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * Class, contains fields, describing all possible types of document's text fields
 */
public class eVisualFieldType {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ ft_Document_Class_Code,
             ft_Issuing_State_Code,
             ft_Document_Number,
             ft_Date_of_Expiry,
             ft_Date_of_Issue,
             ft_Date_of_Birth,
             ft_Place_of_Birth,
             ft_Personal_Number,
             ft_Surname,
             ft_Given_Names,
             ft_Mothers_Name,
             ft_Nationality,
             ft_Sex,
             ft_Height,
             ft_Weight,
             ft_Eyes_Color,
             ft_Hair_Color,
             ft_Address,
             ft_Donor,
             ft_Social_Security_Number,
             ft_DL_Class,
             ft_DL_Endorsed,
             ft_DL_Restriction_Code,
             ft_DL_Under_21_Date,
             ft_Authority,
             ft_Surname_And_Given_Names,
             ft_Nationality_Code,
             ft_Passport_Number,
             ft_Invitation_Number,
             ft_Visa_ID,
             ft_Visa_Class,
             ft_Visa_SubClass,
             ft_MRZ_String1,
             ft_MRZ_String2,
             ft_MRZ_String3,
             ft_MRZ_Type,
             ft_Optional_Data,
             ft_Document_Class_Name,
             ft_Issuing_State_Name,
             ft_Place_of_Issue,
             ft_Document_Number_Checksum,
             ft_Date_of_Birth_Checksum,
             ft_Date_of_Expiry_Checksum,
             ft_Personal_Number_Checksum,
             ft_FinalChecksum,
             ft_Passport_Number_Checksum,
             ft_Invitation_Number_Checksum,
             ft_Visa_ID_Checksum,
             ft_Surname_And_Given_Names_Checksum,
             ft_Visa_Valid_Until_Checksum,
             ft_Other,
             ft_MRZ_Strings,
             ft_Name_Suffix,
             ft_Name_Prefix,
             ft_Date_of_Issue_Checksum,
             ft_Date_of_Issue_CheckDigit,
             ft_Document_Series,
             ft_RegCert_RegNumber,
             ft_RegCert_CarModel,
             ft_RegCert_CarColor,
             ft_RegCert_BodyNumber,
             ft_RegCert_CarType,
             ft_RegCert_MaxWeight,
             ft_Reg_Cert_Weight,
             ft_Address_Area,
             ft_Address_State,
             ft_Address_Building,
             ft_Address_House,
             ft_Address_Flat,
             ft_Place_of_Registration,
             ft_Date_of_Registration,
             ft_Resident_From,
             ft_Resident_Until,
             ft_Authority_Code,
             ft_Place_of_Birth_Area,
             ft_Place_of_Birth_StateCode,
             ft_Address_Street,
             ft_Address_City,
             ft_Address_Jurisdiction_Code,
             ft_Address_Postal_Code,
             ft_Document_Number_CheckDigit,
             ft_Date_of_Birth_CheckDigit,
             ft_Date_of_Expiry_CheckDigit,
             ft_Personal_Number_CheckDigit,
             ft_FinalCheckDigit,
             ft_Passport_Number_CheckDigit,
             ft_Invitation_Number_CheckDigit,
             ft_Visa_ID_CheckDigit,
             ft_Surname_And_Given_Names_CheckDigit,
             ft_Visa_Valid_Until_CheckDigit,
             ft_Permit_DL_Class,
             ft_Permit_Date_of_Expiry,
             ft_Permit_Identifier,
             ft_Permit_Date_of_Issue,
             ft_Permit_Restriction_Code,
             ft_Permit_Endorsed,
             ft_Issue_Timestamp,
             ft_Number_of_Duplicates,
             ft_Medical_Indicator_Codes,
             ft_Non_Resident_Indicator,
             ft_Visa_Type,
             ft_Visa_Valid_From,
             ft_Visa_Valid_Until,
             ft_Duration_of_Stay,
             ft_Number_of_Entries,
             ft_Day,
             ft_Month,
             ft_Year,
             ft_Unique_Customer_Identifier,
             ft_Commercial_Vehicle_Codes,
             ft_AKA_Date_of_Birth,
             ft_AKA_Social_Security_Number,
             ft_AKA_Surname,
             ft_AKA_Given_Names,
             ft_AKA_Name_Suffix,
             ft_AKA_Name_Prefix,
             ft_Mailing_Address_Street,
             ft_Mailing_Address_City,
             ft_Mailing_Address_Jurisdiction_Code,
             ft_Mailing_Address_Postal_Code,
             ft_Audit_Information,
             ft_Inventory_Number,
             ft_Race_Ethnicity,
             ft_Jurisdiction_Vehicle_Class,
             ft_Jurisdiction_Endorsement_Code,
             ft_Jurisdiction_Restriction_Code,
             ft_Family_Name,
             ft_Given_Names_RUS,
             ft_Visa_ID_RUS,
             ft_Fathers_Name,
             ft_Fathers_Name_RUS,
             ft_Surname_And_Given_Names_RUS,
             ft_Place_Of_Birth_RUS,
             ft_Authority_RUS,
             ft_Issuing_State_Code_Numeric,
             ft_Nationality_Code_Numeric,
             ft_Engine_Power,
             ft_Engine_Volume,
             ft_Chassis_Number,
             ft_Engine_Number,
             ft_Engine_Model,
             ft_Vehicle_Category,
             ft_Identity_Card_Number,
             ft_Control_No,
             ft_Parrent_s_Given_Names,
             ft_Second_Surname,
             ft_Middle_Name,
             ft_RegCert_VIN,
             ft_RegCert_VIN_CheckDigit,
             ft_RegCert_VIN_Checksum,
             ft_Line1_CheckDigit,
             ft_Line2_CheckDigit,
             ft_Line3_CheckDigit,
             ft_Line1_Checksum,
             ft_Line2_Checksum,
             ft_Line3_Checksum,
             ft_RegCert_RegNumber_CheckDigit,
             ft_RegCert_RegNumber_Checksum,
             ft_RegCert_Vehicle_ITS_Code,
             ft_Card_Access_Number,
             ft_Marital_Status,
             ft_Company_Name,
             ft_Special_Notes,
             ft_Surname_of_Spose,
             ft_Tracking_Number,
             ft_Booklet_Number,
             ft_Children,
             ft_Copy,
             ft_Serial_Number,
             ft_Dossier_Number,
             ft_AKA_Surname_And_Given_Names,
             ft_Territorial_Validity,
             ft_MRZ_Strings_With_Correct_CheckSums,
             ft_DL_CDL_Restriction_Code,
             ft_DL_Under_18_Date,
             ft_DL_Record_Created,
             ft_DL_Duplicate_Date,
             ft_DL_Iss_Type,
             ft_Military_Book_Number,
             ft_Destination,
             ft_Blood_Group,
             ft_Sequence_Number,
             ft_RegCert_BodyType,
             ft_RegCert_CarMark,
             ft_Transaction_Number,
             ft_Age,
             ft_Folio_Number,
             ft_Voter_Key,
             ft_Address_Municipality,
             ft_Address_Location,
             ft_Section,
             ft_OCR_Number,
             ft_Federal_Elections,
             ft_Reference_Number,
             ft_Optional_Data_Checksum,
             ft_Optional_Data_CheckDigit,
             ft_Visa_Number,
             ft_Visa_Number_Checksum,
             ft_Visa_Number_CheckDigit,
             ft_Voter,
             ft_Previous_Type,
             ft_FieldFromMRZ,
             ft_CurrentDate,
             ft_Status_Date_of_Expiry,
             ft_Banknote_Number,
             ft_CSC_Code,
             ft_Artistic_Name,
             ft_Academic_Title,
             ft_Address_Country,
             ft_Address_Zipcode,
             ft_eID_Residence_Permit1,
             ft_eID_Residence_Permit2,
             ft_eID_PlaceOfBirth_Street,
             ft_eID_PlaceOfBirth_City,
             ft_eID_PlaceOfBirth_State,
             ft_eID_PlaceOfBirth_Country,
             ft_eID_PlaceOfBirth_Zipcode,
             ft_CDL_Class,
             ft_DL_Under_19_Date,
             ft_Weight_pounds,
             ft_Limited_Duration_Document_Indicator,
             ft_Endorsement_Expiration_Date,
             ft_Revision_Date,
             ft_Compliance_Type,
             ft_Family_name_truncation,
             ft_First_name_truncation,
             ft_Middle_name_truncation,
             ft_Exam_Date,
             ft_Organization,
             ft_Department,
             ft_Pay_Grade,
             ft_Rank,
             ft_Benefits_Number,
             ft_Sponsor_Service,
             ft_Sponsor_Status,
             ft_Sponsor,
             ft_Relationship,
             ft_USCIS,
             ft_Category,
             ft_Conditions,
             ft_Identifier,
             ft_Configuration,
             ft_Discretionary_data,
             ft_Line1_Optional_Data,
             ft_Line2_Optional_Data,
             ft_Line3_Optional_Data,
             ft_EQV_Code,
             ft_ALT_Code,
             ft_Binary_Code,
             ft_Pseudo_Code,
             ft_Fee,
             ft_Stamp_Number,
             ft_GNIB_Number,
             ft_Dept_Number,
             ft_Telex_Code,
             ft_Allergies,
             ft_Sp_Code,
             ft_Court_Code,
             ft_Cty,
             ft_Sponsor_SSN,
             ft_DoD_Number,
             ft_MC_Novice_Date,
             ft_DUF_Number,
             ft_AGY,
             ft_PNR_Code,
             ft_From_Airport_Code,
             ft_To_Airport_Code,
             ft_Flight_Number,
             ft_Date_of_Flight,
             ft_Seat_Number,
             ft_Date_of_Issue_Boarding_Pass,
             ft_CCW_Until,
             ft_Reference_Number_Checksum,
             ft_Reference_Number_CheckDigit,
             ft_Room_Number,
             ft_Religion,
             ft_RemainderTerm,
             ft_Electronic_Ticket_Indicator,
             ft_Compartment_Code,
             ft_CheckIn_Sequence_Number,
             ft_Airline_Designator_of_boarding_pass_issuer,
             ft_Airline_Numeric_Code,
             ft_Ticket_Number,
             ft_Frequent_Flyer_Airline_Designator,
             ft_Frequent_Flyer_Number,
             ft_Free_Baggage_Allowance,
             ft_PDF417Codec,
             ft_Identity_Card_Number_Checksum,
             ft_Identity_Card_Number_CheckDigit,
             ft_Veteran,
             ft_DLClassCode_A1_From,
             ft_DLClassCode_A1_To,
             ft_DLClassCode_A1_Notes,
             ft_DLClassCode_A_From,
             ft_DLClassCode_A_To,
             ft_DLClassCode_A_Notes,
             ft_DLClassCode_B_From,
             ft_DLClassCode_B_To,
             ft_DLClassCode_B_Notes,
             ft_DLClassCode_C1_From,
             ft_DLClassCode_C1_To,
             ft_DLClassCode_C1_Notes,
             ft_DLClassCode_C_From,
             ft_DLClassCode_C_To,
             ft_DLClassCode_C_Notes,
             ft_DLClassCode_D1_From,
             ft_DLClassCode_D1_To,
             ft_DLClassCode_D1_Notes,
             ft_DLClassCode_D_From,
             ft_DLClassCode_D_To,
             ft_DLClassCode_D_Notes,
             ft_DLClassCode_BE_From,
             ft_DLClassCode_BE_To,
             ft_DLClassCode_BE_Notes,
             ft_DLClassCode_C1E_From,
             ft_DLClassCode_C1E_To,
             ft_DLClassCode_C1E_Notes,
             ft_DLClassCode_CE_From,
             ft_DLClassCode_CE_To,
             ft_DLClassCode_CE_Notes,
             ft_DLClassCode_D1E_From,
             ft_DLClassCode_D1E_To,
             ft_DLClassCode_D1E_Notes,
             ft_DLClassCode_DE_From,
             ft_DLClassCode_DE_To,
             ft_DLClassCode_DE_Notes,
             ft_DLClassCode_M_From,
             ft_DLClassCode_M_To,
             ft_DLClassCode_M_Notes,
             ft_DLClassCode_L_From,
             ft_DLClassCode_L_To,
             ft_DLClassCode_L_Notes,
             ft_DLClassCode_T_From,
             ft_DLClassCode_T_To,
             ft_DLClassCode_T_Notes,
             ft_DLClassCode_AM_From,
             ft_DLClassCode_AM_To,
             ft_DLClassCode_AM_Notes,
             ft_DLClassCode_A2_From,
             ft_DLClassCode_A2_To,
             ft_DLClassCode_A2_Notes,
             ft_DLClassCode_B1_From,
             ft_DLClassCode_B1_To,
             ft_DLClassCode_B1_Notes,
             ft_Surname_at_Birth,
             ft_Civil_Status,
             ft_Number_of_Seats,
             ft_Number_of_Standing_Places,
             ft_Max_Speed,
             ft_Fuel_Type,
             ft_EC_Environmental_Type,
             ft_Power_Weight_Ratio,
             ft_Max_Mass_of_Trailer_Braked,
             ft_Max_Mass_of_Trailer_Unbraked,
             ft_Transmission_Type,
             ft_Trailer_Hitch,
             ft_Accompanied_by,
             ft_Police_District,
             ft_First_Issue_Date,
             ft_Payload_Capacity,
             ft_Number_of_Axels,
             ft_Permissible_Axle_Load,
             ft_Precinct,
             ft_Invited_by,
             ft_Purpose_of_Entry,
             ft_Skin_Color,
             ft_Complexion,
             ft_Airport_From,
             ft_Airport_To,
             ft_Airline_Name,
             ft_Airline_Name_Frequent_Flyer,
             ft_In_Tanks,
             ft_Exept_In_Tanks,
             ft_Fast_Track,
             ft_Owner,
             ft_MRZ_Strings_ICAO_RFID,
             ft_Number_of_Card_Issuance,
             ft_Number_of_Card_Issuance_Checksum,
             ft_Number_of_Card_Issuance_CheckDigit,
             ft_Century_Date_of_Birth})
    public @interface VisualFieldTypes{}
    
public static final int ft_Document_Class_Code = 0;
public static final int ft_Issuing_State_Code = 1;
public static final int ft_Document_Number = 2;
public static final int ft_Date_of_Expiry = 3;
public static final int ft_Date_of_Issue = 4;
public static final int ft_Date_of_Birth = 5;
public static final int ft_Place_of_Birth = 6;
public static final int ft_Personal_Number = 7;
public static final int ft_Surname = 8;
public static final int ft_Given_Names = 9;
public static final int ft_Mothers_Name = 10;
public static final int ft_Nationality = 11;
public static final int ft_Sex = 12;
public static final int ft_Height = 13;
public static final int ft_Weight = 14;
public static final int ft_Eyes_Color = 15;
public static final int ft_Hair_Color = 16;
public static final int ft_Address = 17;
public static final int ft_Donor = 18;
public static final int ft_Social_Security_Number = 19;
public static final int ft_DL_Class = 20;
public static final int ft_DL_Endorsed = 21;
public static final int ft_DL_Restriction_Code = 22;
public static final int ft_DL_Under_21_Date = 23;
public static final int ft_Authority = 24;
public static final int ft_Surname_And_Given_Names = 25;
public static final int ft_Nationality_Code = 26;
public static final int ft_Passport_Number = 27;
public static final int ft_Invitation_Number = 28;
public static final int ft_Visa_ID = 29;
public static final int ft_Visa_Class = 30;
public static final int ft_Visa_SubClass = 31;
public static final int ft_MRZ_String1 = 32;
public static final int ft_MRZ_String2 = 33;
public static final int ft_MRZ_String3 = 34;
public static final int ft_MRZ_Type = 35;
public static final int ft_Optional_Data = 36;
public static final int ft_Document_Class_Name = 37;
public static final int ft_Issuing_State_Name = 38;
public static final int ft_Place_of_Issue = 39;
public static final int ft_Document_Number_Checksum = 40;
public static final int ft_Date_of_Birth_Checksum = 41;
public static final int ft_Date_of_Expiry_Checksum = 42;
public static final int ft_Personal_Number_Checksum = 43;
public static final int ft_FinalChecksum = 44;
public static final int ft_Passport_Number_Checksum = 45;
public static final int ft_Invitation_Number_Checksum = 46;
public static final int ft_Visa_ID_Checksum = 47;
public static final int ft_Surname_And_Given_Names_Checksum = 48;
public static final int ft_Visa_Valid_Until_Checksum = 49;
public static final int ft_Other = 50;
public static final int ft_MRZ_Strings = 51;
public static final int ft_Name_Suffix = 52;
public static final int ft_Name_Prefix = 53;
public static final int ft_Date_of_Issue_Checksum = 54;
public static final int ft_Date_of_Issue_CheckDigit = 55;
public static final int ft_Document_Series = 56;
public static final int ft_RegCert_RegNumber = 57;
public static final int ft_RegCert_CarModel = 58;
public static final int ft_RegCert_CarColor = 59;
public static final int ft_RegCert_BodyNumber = 60;
public static final int ft_RegCert_CarType = 61;
public static final int ft_RegCert_MaxWeight = 62;
public static final int ft_Reg_Cert_Weight = 63;
public static final int ft_Address_Area = 64;
public static final int ft_Address_State = 65;
public static final int ft_Address_Building = 66;
public static final int ft_Address_House = 67;
public static final int ft_Address_Flat = 68;
public static final int ft_Place_of_Registration = 69;
public static final int ft_Date_of_Registration = 70;
public static final int ft_Resident_From = 71;
public static final int ft_Resident_Until = 72;
public static final int ft_Authority_Code = 73;
public static final int ft_Place_of_Birth_Area = 74;
public static final int ft_Place_of_Birth_StateCode = 75;
public static final int ft_Address_Street = 76;
public static final int ft_Address_City = 77;
public static final int ft_Address_Jurisdiction_Code = 78;
public static final int ft_Address_Postal_Code = 79;
public static final int ft_Document_Number_CheckDigit = 80;
public static final int ft_Date_of_Birth_CheckDigit = 81;
public static final int ft_Date_of_Expiry_CheckDigit = 82;
public static final int ft_Personal_Number_CheckDigit = 83;
public static final int ft_FinalCheckDigit = 84;
public static final int ft_Passport_Number_CheckDigit = 85;
public static final int ft_Invitation_Number_CheckDigit = 86;
public static final int ft_Visa_ID_CheckDigit = 87;
public static final int ft_Surname_And_Given_Names_CheckDigit = 88;
public static final int ft_Visa_Valid_Until_CheckDigit = 89;
public static final int ft_Permit_DL_Class = 90;
public static final int ft_Permit_Date_of_Expiry = 91;
public static final int ft_Permit_Identifier = 92;
public static final int ft_Permit_Date_of_Issue = 93;
public static final int ft_Permit_Restriction_Code = 94;
public static final int ft_Permit_Endorsed = 95;
public static final int ft_Issue_Timestamp = 96;
public static final int ft_Number_of_Duplicates = 97;
public static final int ft_Medical_Indicator_Codes = 98;
public static final int ft_Non_Resident_Indicator = 99;
public static final int ft_Visa_Type = 100;
public static final int ft_Visa_Valid_From = 101;
public static final int ft_Visa_Valid_Until = 102;
public static final int ft_Duration_of_Stay = 103;
public static final int ft_Number_of_Entries = 104;
public static final int ft_Day = 105;
public static final int ft_Month = 106;
public static final int ft_Year = 107;
public static final int ft_Unique_Customer_Identifier = 108;
public static final int ft_Commercial_Vehicle_Codes = 109;
public static final int ft_AKA_Date_of_Birth = 110;
public static final int ft_AKA_Social_Security_Number = 111;
public static final int ft_AKA_Surname = 112;
public static final int ft_AKA_Given_Names = 113;
public static final int ft_AKA_Name_Suffix = 114;
public static final int ft_AKA_Name_Prefix = 115;
public static final int ft_Mailing_Address_Street = 116;
public static final int ft_Mailing_Address_City = 117;
public static final int ft_Mailing_Address_Jurisdiction_Code = 118;
public static final int ft_Mailing_Address_Postal_Code = 119;
public static final int ft_Audit_Information = 120;
public static final int ft_Inventory_Number = 121;
public static final int ft_Race_Ethnicity = 122;
public static final int ft_Jurisdiction_Vehicle_Class = 123;
public static final int ft_Jurisdiction_Endorsement_Code = 124;
public static final int ft_Jurisdiction_Restriction_Code = 125;
public static final int ft_Family_Name = 126;
public static final int ft_Given_Names_RUS = 127;
public static final int ft_Visa_ID_RUS = 128;
public static final int ft_Fathers_Name = 129;
public static final int ft_Fathers_Name_RUS = 130;
public static final int ft_Surname_And_Given_Names_RUS = 131;
public static final int ft_Place_Of_Birth_RUS = 132;
public static final int ft_Authority_RUS = 133;
public static final int ft_Issuing_State_Code_Numeric = 134;
public static final int ft_Nationality_Code_Numeric = 135;
public static final int ft_Engine_Power = 136;
public static final int ft_Engine_Volume = 137;
public static final int ft_Chassis_Number = 138;
public static final int ft_Engine_Number = 139;
public static final int ft_Engine_Model = 140;
public static final int ft_Vehicle_Category = 141;
public static final int ft_Identity_Card_Number = 142;
public static final int ft_Control_No = 143;
public static final int ft_Parrent_s_Given_Names = 144;
public static final int ft_Second_Surname = 145;
public static final int ft_Middle_Name = 146;
public static final int ft_RegCert_VIN = 147;
public static final int ft_RegCert_VIN_CheckDigit = 148;
public static final int ft_RegCert_VIN_Checksum = 149;
public static final int ft_Line1_CheckDigit = 150;
public static final int ft_Line2_CheckDigit = 151;
public static final int ft_Line3_CheckDigit = 152;
public static final int ft_Line1_Checksum = 153;
public static final int ft_Line2_Checksum = 154;
public static final int ft_Line3_Checksum = 155;
public static final int ft_RegCert_RegNumber_CheckDigit = 156;
public static final int ft_RegCert_RegNumber_Checksum = 157;
public static final int ft_RegCert_Vehicle_ITS_Code = 158;
public static final int ft_Card_Access_Number = 159;
public static final int ft_Marital_Status = 160;
public static final int ft_Company_Name = 161;
public static final int ft_Special_Notes = 162;
public static final int ft_Surname_of_Spose = 163;
public static final int ft_Tracking_Number = 164;
public static final int ft_Booklet_Number = 165;
public static final int ft_Children = 166;
public static final int ft_Copy = 167;
public static final int ft_Serial_Number = 168;
public static final int ft_Dossier_Number = 169;
public static final int ft_AKA_Surname_And_Given_Names = 170;
public static final int ft_Territorial_Validity = 171;
public static final int ft_MRZ_Strings_With_Correct_CheckSums = 172;
public static final int ft_DL_CDL_Restriction_Code = 173;
public static final int ft_DL_Under_18_Date = 174;
public static final int ft_DL_Record_Created = 175;
public static final int ft_DL_Duplicate_Date = 176;
public static final int ft_DL_Iss_Type = 177;
public static final int ft_Military_Book_Number = 178;
public static final int ft_Destination = 179;
public static final int ft_Blood_Group = 180;
public static final int ft_Sequence_Number = 181;
public static final int ft_RegCert_BodyType = 182;
public static final int ft_RegCert_CarMark = 183;
public static final int ft_Transaction_Number = 184;
public static final int ft_Age = 185;
public static final int ft_Folio_Number = 186;
public static final int ft_Voter_Key = 187;
public static final int ft_Address_Municipality = 188;
public static final int ft_Address_Location = 189;
public static final int ft_Section = 190;
public static final int ft_OCR_Number = 191;
public static final int ft_Federal_Elections = 192;
public static final int ft_Reference_Number = 193;
public static final int ft_Optional_Data_Checksum = 194;
public static final int ft_Optional_Data_CheckDigit = 195;
public static final int ft_Visa_Number = 196;
public static final int ft_Visa_Number_Checksum = 197;
public static final int ft_Visa_Number_CheckDigit = 198;
public static final int ft_Voter = 199;
public static final int ft_Previous_Type = 200;
public static final int ft_FieldFromMRZ = 220;
public static final int ft_CurrentDate = 221;
public static final int ft_Status_Date_of_Expiry = 251;
public static final int ft_Banknote_Number = 252;
public static final int ft_CSC_Code = 253;
public static final int ft_Artistic_Name = 254;
public static final int ft_Academic_Title = 255;
public static final int ft_Address_Country = 256;
public static final int ft_Address_Zipcode = 257;
public static final int ft_eID_Residence_Permit1 = 258;
public static final int ft_eID_Residence_Permit2 = 259;
public static final int ft_eID_PlaceOfBirth_Street = 260;
public static final int ft_eID_PlaceOfBirth_City = 261;
public static final int ft_eID_PlaceOfBirth_State = 262;
public static final int ft_eID_PlaceOfBirth_Country = 263;
public static final int ft_eID_PlaceOfBirth_Zipcode = 264;
public static final int ft_CDL_Class = 265;
public static final int ft_DL_Under_19_Date = 266;
public static final int ft_Weight_pounds = 267;
public static final int ft_Limited_Duration_Document_Indicator = 268;
public static final int ft_Endorsement_Expiration_Date = 269;
public static final int ft_Revision_Date = 270;
public static final int ft_Compliance_Type = 271;
public static final int ft_Family_name_truncation = 272;
public static final int ft_First_name_truncation = 273;
public static final int ft_Middle_name_truncation = 274;
public static final int ft_Exam_Date = 275;
public static final int ft_Organization = 276;
public static final int ft_Department = 277;
public static final int ft_Pay_Grade = 278;
public static final int ft_Rank = 279;
public static final int ft_Benefits_Number = 280;
public static final int ft_Sponsor_Service = 281;
public static final int ft_Sponsor_Status = 282;
public static final int ft_Sponsor = 283;
public static final int ft_Relationship = 284;
public static final int ft_USCIS = 285;
public static final int ft_Category = 286;
public static final int ft_Conditions = 287;
public static final int ft_Identifier = 288;
public static final int ft_Configuration = 289;
public static final int ft_Discretionary_data = 290;
public static final int ft_Line1_Optional_Data = 291;
public static final int ft_Line2_Optional_Data = 292;
public static final int ft_Line3_Optional_Data = 293;
public static final int ft_EQV_Code = 294;
public static final int ft_ALT_Code = 295;
public static final int ft_Binary_Code = 296;
public static final int ft_Pseudo_Code = 297;
public static final int ft_Fee = 298;
public static final int ft_Stamp_Number = 299;
public static final int ft_GNIB_Number = 340;
public static final int ft_Dept_Number = 341;
public static final int ft_Telex_Code = 342;
public static final int ft_Allergies = 343;
public static final int ft_Sp_Code = 344;
public static final int ft_Court_Code = 345;
public static final int ft_Cty = 346;
public static final int ft_Sponsor_SSN = 347;
public static final int ft_DoD_Number = 348;
public static final int ft_MC_Novice_Date = 349;
public static final int ft_DUF_Number = 350;
public static final int ft_AGY = 351;
public static final int ft_PNR_Code = 352;
public static final int ft_From_Airport_Code = 353;
public static final int ft_To_Airport_Code = 354;
public static final int ft_Flight_Number = 355;
public static final int ft_Date_of_Flight = 356;
public static final int ft_Seat_Number = 357;
public static final int ft_Date_of_Issue_Boarding_Pass = 358;
public static final int ft_CCW_Until = 359;
public static final int ft_Reference_Number_Checksum = 360;
public static final int ft_Reference_Number_CheckDigit = 361;
public static final int ft_Room_Number = 362;
public static final int ft_Religion = 363;
public static final int ft_RemainderTerm = 364;
public static final int ft_Electronic_Ticket_Indicator = 365;
public static final int ft_Compartment_Code = 366;
public static final int ft_CheckIn_Sequence_Number = 367;
public static final int ft_Airline_Designator_of_boarding_pass_issuer = 368;
public static final int ft_Airline_Numeric_Code = 369;
public static final int ft_Ticket_Number = 370;
public static final int ft_Frequent_Flyer_Airline_Designator = 371;
public static final int ft_Frequent_Flyer_Number = 372;
public static final int ft_Free_Baggage_Allowance = 373;
public static final int ft_PDF417Codec = 374;
public static final int ft_Identity_Card_Number_Checksum = 375;
public static final int ft_Identity_Card_Number_CheckDigit = 376;
public static final int ft_Veteran = 377;
public static final int ft_DLClassCode_A1_From = 378;
public static final int ft_DLClassCode_A1_To = 379;
public static final int ft_DLClassCode_A1_Notes = 380;
public static final int ft_DLClassCode_A_From = 381;
public static final int ft_DLClassCode_A_To = 382;
public static final int ft_DLClassCode_A_Notes = 383;
public static final int ft_DLClassCode_B_From = 384;
public static final int ft_DLClassCode_B_To = 385;
public static final int ft_DLClassCode_B_Notes = 386;
public static final int ft_DLClassCode_C1_From = 387;
public static final int ft_DLClassCode_C1_To = 388;
public static final int ft_DLClassCode_C1_Notes = 389;
public static final int ft_DLClassCode_C_From = 390;
public static final int ft_DLClassCode_C_To = 391;
public static final int ft_DLClassCode_C_Notes = 392;
public static final int ft_DLClassCode_D1_From = 393;
public static final int ft_DLClassCode_D1_To = 394;
public static final int ft_DLClassCode_D1_Notes = 395;
public static final int ft_DLClassCode_D_From = 396;
public static final int ft_DLClassCode_D_To = 397;
public static final int ft_DLClassCode_D_Notes = 398;
public static final int ft_DLClassCode_BE_From = 399;
public static final int ft_DLClassCode_BE_To = 400;
public static final int ft_DLClassCode_BE_Notes = 401;
public static final int ft_DLClassCode_C1E_From = 402;
public static final int ft_DLClassCode_C1E_To = 403;
public static final int ft_DLClassCode_C1E_Notes = 404;
public static final int ft_DLClassCode_CE_From = 405;
public static final int ft_DLClassCode_CE_To = 406;
public static final int ft_DLClassCode_CE_Notes = 407;
public static final int ft_DLClassCode_D1E_From = 408;
public static final int ft_DLClassCode_D1E_To = 409;
public static final int ft_DLClassCode_D1E_Notes = 410;
public static final int ft_DLClassCode_DE_From = 411;
public static final int ft_DLClassCode_DE_To = 412;
public static final int ft_DLClassCode_DE_Notes = 413;
public static final int ft_DLClassCode_M_From = 414;
public static final int ft_DLClassCode_M_To = 415;
public static final int ft_DLClassCode_M_Notes = 416;
public static final int ft_DLClassCode_L_From = 417;
public static final int ft_DLClassCode_L_To = 418;
public static final int ft_DLClassCode_L_Notes = 419;
public static final int ft_DLClassCode_T_From = 420;
public static final int ft_DLClassCode_T_To = 421;
public static final int ft_DLClassCode_T_Notes = 422;
public static final int ft_DLClassCode_AM_From = 423;
public static final int ft_DLClassCode_AM_To = 424;
public static final int ft_DLClassCode_AM_Notes = 425;
public static final int ft_DLClassCode_A2_From = 426;
public static final int ft_DLClassCode_A2_To = 427;
public static final int ft_DLClassCode_A2_Notes = 428;
public static final int ft_DLClassCode_B1_From = 429;
public static final int ft_DLClassCode_B1_To = 430;
public static final int ft_DLClassCode_B1_Notes = 431;
public static final int ft_Surname_at_Birth = 432;
public static final int ft_Civil_Status = 433;
public static final int ft_Number_of_Seats = 434;
public static final int ft_Number_of_Standing_Places = 435;
public static final int ft_Max_Speed = 436;
public static final int ft_Fuel_Type = 437;
public static final int ft_EC_Environmental_Type = 438;
public static final int ft_Power_Weight_Ratio = 439;
public static final int ft_Max_Mass_of_Trailer_Braked = 440;
public static final int ft_Max_Mass_of_Trailer_Unbraked = 441;
public static final int ft_Transmission_Type = 442;
public static final int ft_Trailer_Hitch = 443;
public static final int ft_Accompanied_by = 444;
public static final int ft_Police_District = 445;
public static final int ft_First_Issue_Date = 446;
public static final int ft_Payload_Capacity = 447;
public static final int ft_Number_of_Axels = 448;
public static final int ft_Permissible_Axle_Load = 449;
public static final int ft_Precinct = 450;
public static final int ft_Invited_by = 451;
public static final int ft_Purpose_of_Entry = 452;
public static final int ft_Skin_Color = 453;
public static final int ft_Complexion = 454;
public static final int ft_Airport_From = 455;
public static final int ft_Airport_To = 456;
public static final int ft_Airline_Name = 457;
public static final int ft_Airline_Name_Frequent_Flyer = 458;
public static final int ft_In_Tanks = 460;
public static final int ft_Exept_In_Tanks = 461;
public static final int ft_Fast_Track = 462;
public static final int ft_Owner = 463;
public static final int ft_MRZ_Strings_ICAO_RFID = 464;
public static final int ft_Number_of_Card_Issuance = 465;
public static final int ft_Number_of_Card_Issuance_Checksum = 466;
public static final int ft_Number_of_Card_Issuance_CheckDigit = 467;
public static final int ft_Century_Date_of_Birth = 468;
};

