def delete_empty_line_in_babylone():
    file_name = "babylon.txt"
    output_file_name = "babylon_without_empty_line.txt"
    with open(output_file_name, "a") as output_file:                
        with open(file_name, "r") as file:
            line = file.readline()
            while line:
                line = file.readline()
                if line != "\n":
                    output_file.write(line)

delete_empty_line_in_babylone()