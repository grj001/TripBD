drop table if exists ods_company_${yyyymmdd};
create external table ods_company_${yyyymmdd} (
    hotel_id int,
        hotelname string ,
        province string,
        city string,
        county string,
        section string,
        address string,
        hotelphoneno string,
        hotellevel string,
        company_id int,
) stored as parquet
location '/sqoop/btrip_pg/${yyyymmdd}/tb_hotel';