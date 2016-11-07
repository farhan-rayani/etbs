select s.Staff_Name,s.staff_id,p.source_phone_no,p.service_provider,sum(call_amount),p.call_type 
from staff_details s 
inner join phone_bill_details p on  p.staff_id= s.staff_id and p.yyyy_mm=201509
where  s.is_dummy=1 and p.call_type='Business'
group by s.staff_id, s.Staff_Name, p.source_phone_no,p.service_provider,p.call_type
order by s.Staff_Name