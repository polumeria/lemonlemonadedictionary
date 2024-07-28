-- все переменные записанные через @ передаются в метод и подставляются на это место 


-- база. 
select termin as "термин", description as "определение" from termin
	where is_deleted = false


-- база + сортировать по алфавиту (а-я)
select termin as "термин", description as "определение" from termin
	where is_deleted = false
order by termin asc

-- база + сортировать по алфавиту (я-а)
select termin as "термин", description as "определение" from termin
	where is_deleted = false
order by termin desc

-- строгий (или нет хз) поиск
select termin as "термин", description as "определение" as document
    from public.termin
where termin LIKE '%@search_value%' 
    or description LIKE '%@search_value%'

-- удаленные слова (просмотр)
select termin as "термин", id from termin
	where is_deleted = true

-- восстановить слово
UPDATE termin 
SET is_deleted = false 
    where id = '@id';

-- удалить слово
UPDATE termin 
SET is_deleted = true 
    where id = '@id';

-- добавить слово
INSERT INTO termin
VALUES
        ('@generated_id', '@termin_name', '@termin_description', false);
-- generated_id - нужно до этого генерировать в коде проги


-- редактировать слово
UPDATE termin 
SET termin = '@updated_termin', description = '@updated_description'
    where id = '@id';
-- сначала в поле выводится считанное из ячейки значение, после все значения считываются по новому с префиксом updated



-- можно сделать вызов вызова вызова (ака функции в скл)





