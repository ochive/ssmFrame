/*FILE_PATHS附件路径表的增删改查
工业产品主表：ZJ_IND_ITEM_INF
机动车主表：BN_ZJ_VEHICLE_INF
*/
--根据事项名称和事项主键查询,该办件的附件时候已经下载过。
select count(*) from FILE_PATHS where ITEM=? AND ITEM_PK=?
--根据附件的主键查询路径信息
SELECT * FROM FILE_PATHS WHERE ATTACH_ID=?
--select ID_FILEPATHS,FILENAME FROM FILE_PATHS WHERE ITEM=? AND ITEM_PK=? AND TYPE=0
--查询办件的批量下载附件
--select ID_FILEPATHS,FILENAME FROM FILE_PATHS WHERE ITEM=? AND ITEM_PK=? AND TYPE=1

--附件下载后，插入路径表
insert into FILE_PATHS (
    	ITEM,ITEM_PK,FILE_PATH,FILE_NAME,TYPE,ATTACH_ID)
    	VALUES(?,?,?,?,?,0)
--批量下载包生成后，插入路径表
insert into FILE_PATHS 
(ID_FILEPATHS,ITEM,ITEM_PK,PATH,FILENAME,TYPE)
VALUES(
SYS_GUID,?,?,?,?,1)
--删
--DELETE FROM FILE_PATHS WHERE ITEM=? AND ITEM_PK=?
--改
--UPDATE FILE_PATHS SET  PATH=? WHERE ID_FILEPATHS=?

/*T_YWBL_ATTACHMENT附件表*/
--查询还没被下载的附件,一次查20条,防止内存溢出.
SELECT * FROM 
(
SELECT A.*, ROWNUM RN 
FROM (SELECT IDT_YWBL_ATTACHMENT
		,ORD,SJ_FILE_NAME,SOURCE_FILE
		,SOURCE_FILE_NAME,DOWNLOADED 
    FROM T_YWBL_ATTACHMENT WHERE SOURCE_FILE IS NOT NULL AND DOWNLOADED = '0') A 
WHERE ROWNUM <= 20 
) 
WHERE RN >= 1
--根据附件表主键查询
select * from T_YWBL_ATTACHMENT where idt_ywbl_attachment='b2775a346eb04d6885820def8f9b52ce'

--查工业产品附件
select 
ord--附件顺序
,sj_file_name--附件名称
，source_file--源文件
,idt_ywbl_attachment --附件主键
from T_YWBL_ATTACHMENT where idzj_ind_item_inf = ？--工业产品外键



--查机动车附件
select 
ord,sj_file_name，source_file,idt_ywbl_attachment 
from T_YWBL_ATTACHMENT where idzj_vehicle_inf = ？--机动车外键

--下载附件成功后，更新附件表
UPDATE T_YWBL_ATTACHMENT SET DOWNLOADED = '1' WHERE IDT_YWBL_ATTACHMENT = #{aid}



--删除测试时的数据
update T_YWBL_ATTACHMENT set downloaded=0 ;
truncate table FILE_PATHS;














