SELECT sum(call_amount) as Business FROM etbs_new.phone_bill_details where yyyy_mm=201509 and call_type='Business';
SELECT sum(call_amount) as Personal FROM etbs_new.phone_bill_details where yyyy_mm=201509 and call_type='Personal';
SELECT sum(call_amount) as UnCategorized FROM etbs_new.phone_bill_details where yyyy_mm=201509 and call_type='UNKNOWN';
SELECT sum(call_amount) as Total FROM etbs_new.phone_bill_details where yyyy_mm=201509
