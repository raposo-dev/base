create table contracts
(
    id                         integer not null
        constraint contracts_pk
            primary key,
    contracting_procedure_type text,
    publication_date           date,
    contracting                text,
    contracted                 text,
    object_brief_description   text,
    initial_contractual_price  numeric,
    signing_date               date
);


create table contract_details
(
    id                                        integer not null
        constraint contract_details_pk
            primary key,
    framework_agreement_procedure_id          text,
    ambient_criteria                          boolean,
    announcement_id                           integer,
    direct_award_fundamentation_type          text,
    contracting_procedure_url                 text,
    observations                              text,
    publication_date                          date,
    end_of_contract_type                      text,
    total_effective_price                     numeric,
    contract_fundamentation_type              text,
    framework_agreement_procedure_description text,
    causes_deadline_change                    text,
    causes_price_change                       text,
    close_date                                date,
    increments                                boolean,
    contracting_procedure_type                text,
    contract_types                            text,
    execution_deadline                        text,
    cpvs                                      text,
    contract_typecs                           boolean,
    object_brief_description                  text,
    income                                    boolean,
    centralized_procedure                     text,
    execution_place                           text,
    non_written_contract_justification_types  text,
    initial_contractual_price                 numeric,
    contract_status                           text,
    signing_date                              date,
    cocontratantes                            boolean,
    description                               text
);

create unique index contract_details_id_uindex
    on contract_details (id);

create table contestants
(
    nif         text,
    id          integer not null
        constraint contestants_pk
            primary key,
    description text
);

create table contracted
(
    nif         text,
    id          integer not null
        constraint contracted_pk
            primary key,
    description text
);


create table contracting
(
    nif         text,
    id          integer not null
        constraint contracting_pk
            primary key,
    description text
);


create table invitees
(
    nif         text,
    id          integer not null
        constraint invitees_pk
            primary key,
    description text
);


create table documents
(
    id          integer not null,
    description text
);


create table contract_details_contestants
(
    contract_details_id integer,
    contestants_id      integer
);


create table contract_details_contracted
(
    contract_details_id integer,
    contracted_id       integer
);


create table contract_details_contracting
(
    contract_details_id integer,
    contracting_id      integer
);


create table contract_details_documents
(
    contract_details_id integer,
    documents_id        integer
);


create table contract_details_invitees
(
    contract_details_id integer,
    invitees_id         integer
);


