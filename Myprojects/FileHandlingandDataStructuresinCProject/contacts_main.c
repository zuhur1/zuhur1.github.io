#include <stdio.h>
#include <string.h>

#include "contacts.h"

#define MAX_CMD_LEN 128

/*
 * This is in general *very* similar to the list_main file seen in lab 2
 * One big difference is the notion of switching between contact logs in one
 * run of the program.
 * You have to create or load a contact log from a file before you can do things
 * like add, lookup, or write.
 * The code below has to check if contact log is NULL before the operations
 * occur. Also, the user has to explicitly clear the current contact log before
 * they can create or load in a new one.
 */
int main(int argc, char **argv) {
    contacts_log_t *log = NULL;
if (argc >=2) {
    char *filename = argv[1];
    // create a char pointer to the filename from the command line
    char *file_extention = strrchr(filename, '.');
    // separates filename from the dot and after and assigns it to file_extention
    if(file_extention !=NULL){
        if (strcmp(file_extention + 1, "txt") == 0) {
            //compares file_extention and the txt to check if the file entered is a text file (the +1 after file_extention does so stcmp doesn't include the '.')
            log = read_contacts_log_from_text(filename);
            //sets the log (current active log) to the return of read_contacts_log_from_text
            if (log != NULL) {
                printf("Contacts log loaded from text file\n");
            } else {
                printf("Failed to read contacts log from text file\n");
                }
        }else if (strcmp(file_extention + 1, "bin") == 0) {
            log = read_contacts_log_from_binary(filename);
            //sets the log (current active log) to the return of read_contacts_log_from_bin
            if (log != NULL) {
                 printf("Contacts log loaded from binary file\n");
            } else {
                printf("Failed to read contacts log from binary file\n");
            }
    } else {
        printf("Error: Unknown contacts log file extension\n");
    }
    }else{
        printf("Unknown command\n");
    } 
    }
    printf("CSCI 2021 Contact Log System\n");
    printf("Commands:\n");
    printf("  create <name>:            creates a new log with specified name\n");
    printf("  log:                      shows the name of the active contacts log\n");
    printf("  add <name> <phone> <zip>: adds a new contact\n");
    printf("  lookup <name>:            searches for a phone number by contact name\n");
    printf("  clear:                    resets current contacts log\n");
    printf("  print:                    shows all contacts in active log\n");
    printf("  write_text:               saves all contacts to text file\n");
    printf("  read_text <file_name>:    loads contacts from text file\n");
    printf("  write_bin:                saves all contacts to binary file\n");
    printf("  read_bin <file_name>:     loads contacts from binary file\n");
    printf("  exit:                     exits the program\n");

    char cmd[MAX_CMD_LEN];
    while (1) {
        printf("contacts> ");
        if (scanf("%s", cmd) == EOF) {
            printf("\n");
            break;
        }
        if (strcmp("exit", cmd) == 0) {
            break;
        }
        else if (strcmp("create", cmd) == 0) {
            scanf("%s", cmd); // Read in new log name
            if (log != NULL) {
                printf("Error: You already have an contacts log.\n");
                printf("You can remove it with the \'clear\' command\n");
            } else {
                log = create_contacts_log(cmd);
                //if log is not NULL, log is set to the return of create_contacts_log
                if (log == NULL) {
                    printf("Contacts log creation failed\n");
                }
            }
        }
        else if(strcmp("log", cmd) == 0){
            if(log == NULL){
                printf("Error: You must create or load a contacts log first\n");
            }else{
                printf("%s\n", get_contacts_log_name(log));
                //if log is not NULL, prints the return of get_contacts_log_name (the log name)
            }
        }
        else if(strcmp("add", cmd)== 0){
            char name[MAX_NAME_LEN];
            unsigned int zip;
            unsigned long phone;
            scanf("%s %lu %u", name, &phone, &zip);
            // scans values from the command and sets them to respective variables
            if(log == NULL){
                printf("Error: You must create or load a contacts log first\n");
            }else{
                add_contact(log, name, phone, zip);
                // if log is not null, creates a new contact to desirerd log using add_contact method
            }
        }
        else if(strcmp("lookup", cmd) == 0){
            char name[MAX_NAME_LEN];
            scanf("%s",name);
            // scans the desire name to lookup from the command line and sets value to name variable
            if(log == NULL){
                printf("Error: You must create or load a contacts log first\n");
            }else {  
                unsigned long phone = find_phone_number(log, name);
                //sets the value of phone to what find_phone_number returns
                if(phone != -1){
                    printf("%s: %lu\n", name, phone);
                    //prints lookup findings in this format
                }
            }
        }
        else if(strcmp("clear", cmd)==0){
            if(log == NULL){
                printf("Error: No contacts log to clear\n");
            }else{
                free_contacts_log(log);
                log = NULL;
                //checks if log is NULL first, if not, frees log and sets value to NULL
            }
        }
        else if(strcmp("print", cmd)== 0){
            if(log == NULL){
                printf("Error: You must create or load a contacts log first\n");
            }else{
                print_contacts_log(log);
                //checks if log is NULL first then prints log by calling print_contacts_log method
            }
        }
        else if(strcmp("write_text", cmd)==0){
            if(log == NULL){
                printf("You must create or load a contacts log first\n");
            }else{
                if(write_contacts_log_to_text(log)==-1){
                    printf("Unable to write contacts to text\n");
                    // checks if there is an active log, if so, writes contacts from active log to text file by calling write_contacts_log_to_text method
                }
            }
        }
        else if(strcmp("read_text", cmd)== 0){
            char file_name[MAX_CMD_LEN];
            scanf("%s", file_name);
            // scans command line for file_name and sets value to char file_name
            if (log != NULL) {
                printf("Error: You must clear current contacts log first\n");
            }else{
                log = read_contacts_log_from_text(file_name);
                // checks that there is no active log, then reads contacts from text and sets them to be the current active log
                if(log == NULL){
                    printf("Failed to read contacts log from text file\n");
                }else{
                    printf("Contacts log loaded from text file\n");
                }
            }
        }
        else if(strcmp("write_bin", cmd) == 0){
            if(log == NULL){
                printf("Error: You must create or load a contacts log first\n");
            }else{
                write_contacts_log_to_binary(log);
                //check that there is an active log then calls write_contacts_log_to_binary to copy contacts in active log to .bin file
            }
        }
        else if(strcmp("read_bin", cmd)==0){
            char file_name[MAX_CMD_LEN];
            scanf("%s", file_name);
            //scans file_name from command
            if (log != NULL) {
                printf("Error: You must clear current contacts log first\n");
            }else{
                log = (read_contacts_log_from_binary(file_name));
                // checks that there is no active log, then creates log pointer by reading and add from binary file. sets log pointer to be current active log
                if(log == NULL){
                    printf("Failed to read contacts log from binary file\n");
                }else{
                    printf("Contacts log loaded from binary file\n");
                }
            }
        }
        else{
           printf("Unknown command %s\n", cmd);
           // if the cmd wasn't caught by other else if statements, then it is an unknown command
        }
    }
    if (log != NULL) {
        free_contacts_log(log);
        //frees contact log when while loop breaks (user wants to exit)
    }
    return 0;
}
