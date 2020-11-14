Alter table public.current_loc
add created timestamp not null default now(),
add modified timestamp not null default now();

