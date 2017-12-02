drop table if exists dwd_roominfo_${yyyymmdd};
create table dwd_roominfo_${yyyymmdd} as
select a.roominfo_id
       ,a.roomno
       ,cast(a.alldayprice as decimal)
       ,cast(a.halfdayprice as decimal)
       ,cast(a.hourprice as decimal)
       ,a.roomarea
       ,a.description
       ,a.note
       ,a.telephoneno
       ,a.bednumber
       ,a.gatecardid
       ,a.roomname
       ,a.layers_id
       ,a.room_type_id
       ,a.valid_flag
       ,b.roomtype_name
       ,c.layers_name
       ,d.siteno
       ,d.address building_address
       ,e.hotel_id
       ,e.hotelname
       ,e.province
       ,e.city
       ,e.county
       ,e.section
       ,e.address hotel_address
       ,e.hotelphoneno
       ,e.hotellevel
       ,f.company_address
       ,f.company_attr
       ,f.company_boss
       ,f.company_name
       ,f.company_phone
from wsc.tb_roominfo a
left join wsc.tb_roomtype b
on a.room_type_id=b.room_type_id
left join wsc.tb_layers c
on a.layers_id=c.layers_id
left join wsc.tb_buildinginfo d
on c.buildinginfo_id=d.buildinginfo_id
left join wsc.tb_hotel e
on d.hotel_id=e.hotel_id
left join wsc.tb_company f
on e.company_id=f.company_id;