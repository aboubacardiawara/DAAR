
def delete_all_xp_files():
    import os
    directory = "xp_files"
    for file in os.listdir(directory):
        file_path = os.path.join(directory, file)
        try:
            if os.path.isfile(file_path):
                os.unlink(file_path)
        except Exception as e:
            print(e)

def generate():
    delete_all_xp_files()
    total_lines = 11592
    pas = 10
    input_file_name = "babylon_without_empty_line.txt"
    with open(input_file_name, "r") as f:
        lines = f.readlines()
        for lines_count in range(0, total_lines, pas):
            output_file_name = f"xp_files/{lines_count}.txt"
            with open(output_file_name, "w") as f:
                contains = lines[:lines_count]
                f.write("".join(contains))

print("Start generating xp files...")
generate()
print("Done generating xp files!")