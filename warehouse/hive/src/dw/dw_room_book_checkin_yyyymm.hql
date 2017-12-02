drop table if exists dw_room_book_checkin_${yyyymm};
create table dw_room_book_checkin_${yyyymm} as
select ${yyyymm} date_month
        ,a.hotel_id
        ,b.room_type_id
        ,count(a.bookedroom_id) book_num
        ,sum(case when a.checkin_room_id is not null then 1 else 0 end) checkin_num
from dwd_booking_room a
left join dwd_roominfo_${当月最后一天} b
on a.roominfo_id=b.roominfo_id
where a.booktime>=${当月第一天} and a.booktime<=${当月最后一天}
group by ${yyyymm} date_month
                 ,a.hotel_id
                 ,b.room_type_id
;