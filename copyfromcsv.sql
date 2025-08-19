\copy pessoa FROM 'csvs/pessoa.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF8'
\copy cargo FROM 'csvs/cargo.csv' DELIMITER ';' CSV HEADER
\copy vencimentos FROM 'csvs/vencimentos.csv' DELIMITER ';' CSV HEADER
\copy cargo_vencimentos FROM 'csvs/cargo_vencimento.csv' DELIMITER ';' CSV HEADER