use starmonydb;

drop function probeUser;
drop function probeEmail;
drop function probeUsername;

delimiter //
create function probeUser(_username varchar(20), _email varchar(50)) returns integer
begin
	declare _total int;

	select count(*) into _total 
		from user u 
        where 	u.username=_username 
			and u.email=_email;

    if ( _total = 0 ) then return 1;
    else return 0;
    end if;
end
//

delimiter //
create function probeUsername(_username varchar(20)) returns integer
begin
	declare _total int;
    
	select count(*) into _total 
		from user u 
		where 	u.username=_username;
        
    if ( _total = 0 ) then return 1;
    else return 0;
    end if;
end
//

delimiter //
create function probeEmail(_email varchar(50)) returns integer
begin
	declare _total int;
	
    select count(*) into _total 
		from user u
        where u.email=_email;
        
    if ( _total = 0 ) then return 1;
    else return 0;
    end if;
end
//

delimiter //
create function getProfile(_fecha date, _state_name int) returns integer
begin
	declare _total, _id_profile int;
    
	select count(*), id_profile into _total, _id_profile
		from profile p
        where 	p.fecha_nacimiento=_fecha 
			and p.state_id_state=_state_name;
            
    if (_total = 0) then
		return 0;
    else
		return _id_profile;
    end if;
end
//

delimiter //
create function addUser(_username varchar(20), _email varchar(50), _password varchar(255), _fecha date, _state_name int) returns integer
begin
	declare _user_id, _id_profile, _new_profile_id int;

	insert into user values(null, _username, _email, _password);
    set _user_id = last_insert_id();

    set _id_profile = getProfile(_fecha, _state_name);
    if (_id_profile = 0) then
		insert into profile values(null,99,_fecha,_state_name);
        set _new_profile_id = last_insert_id();
        
        insert into user_has_profile values(_user_id, _new_profile_id);
    else
		insert into user_has_profile values(_user_id, _id_profile);
    end if;
    
	return _user_id;
end
//

use starmonydb;
select probeUser('dante','dnasystem@gmail.com');
select addUser('dante','dnasystem@gmail.com','Inf13rn0311530','1996-12-13',85);
select addUser('Alison','alison@gmail.com','Inf13rn0311530','2021-01-24',30);
select addUser('Vekza','vekza@gmail.com','Inf13rn0311530','1996-12-13',85);
