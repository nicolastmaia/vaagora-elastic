 
CREATE OR REPLACE FUNCTION trigger_set_timestamp()  
RETURNS TRIGGER AS $$  
BEGIN  
  NEW.modified = NOW();
  RETURN NEW;
END;  
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_timestamp  
BEFORE UPDATE ON current_loc  
FOR EACH ROW  
EXECUTE PROCEDURE trigger_set_timestamp();
