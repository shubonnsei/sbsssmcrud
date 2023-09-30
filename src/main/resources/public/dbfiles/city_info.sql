create materialized view city_info as 
select
    cn.id
    , cn.name
    , cty.continent
    , cty.name as nation
    , cn.district
    , cn.population
    , ctl.language 
from
    city as cn 
    inner join country as cty 
        on cty.code = cn.country_code 
    inner join ( 
        select
            al.country_code
            , case 
                when (um.ofpercentage - coalesce(om.otpercentage, 0)) >= 35 
                    then um.oflanguage 
                else coalesce(om.otlanguage, um.oflanguage) 
                end as language 
        from
            language as al 
            left join ( 
                select
                    nl.country_code
                    , nl.language as otlanguage
                    , nl.percentage as otpercentage
                    , row_number() over ( 
                        partition by
                            country_code 
                        order by
                            percentage desc
                    ) as rnk 
                from
                    language as nl 
                where
                    nl.delete_flg = 'visible' 
                    and nl.is_official = 'T'
            ) om 
                on om.country_code = al.country_code 
                and om.rnk = 1 
            left join ( 
                select
                    nl.country_code
                    , nl.language as oflanguage
                    , nl.percentage as ofpercentage
                    , row_number() over ( 
                        partition by
                            country_code 
                        order by
                            percentage desc
                    ) as rnk 
                from
                    language as nl 
                where
                    nl.delete_flg = 'visible' 
                    and nl.is_official = 'F'
            ) um 
                on um.country_code = al.country_code 
                and um.rnk = 1 
        group by
            al.country_code
            , um.oflanguage
            , om.otlanguage
            , um.ofpercentage
            , om.otpercentage
    ) as ctl 
        on ctl.country_code = cn.country_code 
where
    cn.delete_flg = 'visible' 
order by
    cn.id asc;