
set hive.auto.convert.join=false;
set mapred.reduce.tasks=1;
set hive.exec.mode.local.auto=true;

drop table if exists dw_hotel_roomtype_${yyyymm};
create table dw_hotel_roomtype_${yyyymm} as
select ${yyyymm} date_month
        ,hotel_id
        ,hotel_name
        ,room_type_id
        ,roomtype_name
        ,count(roominfo_id) room_num
from dwd_roominfo_${yyyymmdd} a
group by ${yyyymm}
         ,hotel_id
         ,hotel_name
         ,room_type_id
         ,roomtype_name;
