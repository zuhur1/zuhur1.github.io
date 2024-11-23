#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "contacts.h"


unsigned hash(const char *str) {
    unsigned hash_val = 5381;
    int i = 0;
    while (str[i] != '\0') {
        hash_val = ((hash_val << 5) + hash_val) + str[i];
        i++;
    }
    return hash_val % NUM_BUCKETS;
}

contacts_log_t *create_contacts_log(const char *log_name) {
    contacts_log_t *new_log = malloc(sizeof(contacts_log_t));
    // creates a pointer to a space that can hold contacts struct 
    if (new_log == NULL) {
        return NULL;
    }
    strcpy(new_log->log_name, log_name);
    // use string copy to set log_name of the new instance of the struct to log name entered in the parameters
    for (int i = 0; i < NUM_BUCKETS; i++) {
        // loops through the length of the buckets 
        new_log->buckets[i] = NULL;
        // sets each index to null
    }
    new_log->size = 0;
    // sets the size of the new contact log to 0
    return new_log;
}

const char *get_contacts_log_name(const contacts_log_t *log) {
    if(log == NULL){
        return NULL;
    }
    return log->log_name;
    //checks that log isn't null, then returns name of log
}

int add_contact(contacts_log_t *log, const char *name, unsigned long phone_number, unsigned zip_code) {
    if (zip_code < 10000 || zip_code > 99999 || phone_number > 9999999999 || phone_number < 1000000000) {
        printf("Error: Invalid phone number and/or zip code\n");
        return -1;
    }
    //checks that zip_code and phone_number are within correct range
    node_t *new_contact = malloc(sizeof(node_t));
    // create an instance of a node_t from the new contact, allocates memory for it
    if (new_contact == NULL) {
        return -1;
    }
    // checks if memory allocatation was successful
    strcpy(new_contact->name, name);
    new_contact->phone_number = phone_number;
    new_contact->zip_code = zip_code;
    new_contact->next = NULL;
    //sets the name, phone_number, zip_code for the new node_t instance by setting them to parameters entered
    int index = hash(name);
    //uses hash to determine the bucket index based on name
    if (log->buckets[index] == NULL) {
        log->buckets[index] = new_contact;
        // checks if the head of the linked list is null at that index, sets new_contact to be head if so
    } else {
        node_t *current = log->buckets[index];
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = new_contact;
        //elses traverses through linked list at the index to find next null position and inserts new contact into linked list at that position
    }
    log->size++;
    // increases the size of the contact log
    return 0;
}

long find_phone_number(const contacts_log_t *log, const char *name) { 
    if(log==NULL){
        return -1;
    }
    // checks that log is not null/there is an active contact log
    int index = hash(name);
    // finds the bucket index of the name in the hash (makes searching easier)
    node_t *current = log->buckets[index];
    // sets current node_t pointer to head of the linked list at that index
    while(current != NULL){
        if(strcmp(current-> name, name) == 0){
            return current->phone_number;
            // if the search name and currect-> name match then returns phone_number of current
        }
        //otherwise continues to traverse until current->name equals search name
        current = current->next;
    }
    printf("No phone number for '%s' found\n", name);
    // prints above statement if the whole linked list is traverses and name is not found
    return -1;
}
void print_contacts_log(const contacts_log_t *log) {
    if(log == NULL){
        return;
    }
    //checks that log is not NULL/there is an active log
    printf("All contacts in %s:\n", log->log_name);
    //print statement indicating what log the contacts will be printed from
    for(int i = 0; i < NUM_BUCKETS; i++){
        node_t *current = log->buckets[i];
        // set to node_t pounter head of linked list at each bucket index
        while(current != NULL){
            printf("Name: %s, Phone Number: %lu, Zip Code: %u\n", current->name, current->phone_number, current->zip_code);
            current = current->next;
            // traveres through the entirety of linked list and prints the name, phone number and zipcode of every node
        }
    }
}
void free_contacts_log(contacts_log_t *log) {
    if(log == NULL){
        return;
    }
    // checks that log is not null/there is an active log
    for(int i = 0; i < NUM_BUCKETS; i++){
        node_t *current = log->buckets[i];
        // set to node_t pounter head of linked list at each bucket index
        while(current != NULL){
            node_t *trailer = current;
            current = current->next;
            free(trailer);
            //traverses through linked list. uses another node_t pointer to trail the pointer and frees the trailer while setting current to next node
        }
    }
    free(log);
    //frees the memory allocated for the log after freeing individual nodes
}
int write_contacts_log_to_text(const contacts_log_t *log) {
    if(log == NULL){
        return -1;
    }
    // checks the log is not null/there is an active log
    char file_name[MAX_NAME_LEN + strlen(".txt")];
    strcpy(file_name, log->log_name);
    //sets file_name to equal the name of the log
    strcat(file_name, ".txt");
    // add ".txt" to file name
    FILE *f = fopen(file_name, "w");
    // opens file to write
    if (f == NULL) {
        return -1;
    }
    //checks that file was open properly
    fprintf(f, "%u\n", log->size);
    //write the size of log to the first line
    for (int i = 0; i < NUM_BUCKETS; i++) {
        node_t *current = log->buckets[i];
        // set to node_t pounter head of linked list at each bucket index
        while (current != NULL) {
            fprintf(f, "%s %lu %u\n", current->name, current->phone_number, current->zip_code);
            current = current->next;
            //write the name, phone number and zipcode of current node to the file and continued to traverse the linked list
        }
    }
    printf("Contacts log successfully written to %s\n", file_name);
    fclose(f);
    //print statement indicated writing was a success and closes file
    return 0;
}

contacts_log_t *read_contacts_log_from_text(const char *file_name) {
    FILE *f = fopen(file_name, "r");
    // opens file to read
    if (f == NULL) {
        return NULL;
    }
    //checks file was open properly
    char name[MAX_NAME_LEN];
    strncpy(name, file_name, strlen(file_name)-4);
    // copies filename to name without the last 4 values of file name (filetype)
    name[strlen(file_name)-4] = '\0';
    //adds null terminator to the end of name
    contacts_log_t *new_contact = create_contacts_log(name);
    // creates a pointer to a new empty log 
    unsigned size;
    fscanf(f, "%u", &size);
    //reads the size of log from file and sets value to size variable
    char contactname[MAX_NAME_LEN];
    unsigned int zip;
    unsigned long phone;
    // creates variable to read values to
    for (int i = 0; i < size; i++) {
        fscanf(f, "%s", contactname);
        fscanf(f, "%lu", &phone);
        fscanf(f, "%u", &zip);
        // reads in contactname, phone and zip from file
        add_contact(new_contact, contactname, phone, zip);
        //calls add_contact to add a contact with those parameters to new_contact
    }
    fclose(f);
    return new_contact;
}

int write_contacts_log_to_binary(const contacts_log_t *log) {
    if (log == NULL) {
        return -1;
    }
    // checks the log is not null/there is an active log
    char file_name[MAX_NAME_LEN + strlen(".bin")];
    strcpy(file_name, log->log_name);
    //sets file_name to equal the name of the log
    strcat(file_name, ".bin");
    // add ".bin" to file name
    FILE *f = fopen(file_name, "wb"); 
    //opens file to write binary
    if (f == NULL) {
        printf("Failed to write contacts log to binary file\n");
        return -1;
    }
    // checks if fopen was successful
    fwrite(&log->size, sizeof(unsigned), 1, f);
    //writes the log size to first line
    for (int i = 0; i < NUM_BUCKETS; i++) {
        node_t *current = log->buckets[i];
        // set to node_t pounter head of linked list at each bucket index
        while (current != NULL) {
            unsigned int length = strlen(current->name);
            fwrite(&length, sizeof(unsigned int), 1, f);
            fwrite(current->name, sizeof(char), strlen(current->name), f);
            fwrite(&current->phone_number, sizeof(unsigned long), 1, f);
            fwrite(&current->zip_code, sizeof(unsigned int), 1, f);
            //traverses through linked list at bucket index and writes length, name, phonenumber, and zipcode to the binary file
            current = current->next;
        }
    }
    printf("Contacts log successfully written to %s\n", file_name);
    fclose(f);
    return 0;
}


contacts_log_t *read_contacts_log_from_binary(const char *file_name) {
    FILE *f = fopen(file_name, "rb");
    // opens file to read binary
    if (f == NULL) {
        return NULL;
    }
    //checks file was open properly
    char name[MAX_NAME_LEN];
    strncpy(name, file_name, strlen(file_name)-4);
    // copies filename to name without the last 4 values of file name (filetype)
    name[strlen(file_name)-4] = '\0';
    //adds null terminator to the end of name
    contacts_log_t *new_contacts = create_contacts_log(name);
    // creates a pointer to a new empty log 
    if(new_contacts == NULL){
        fclose(f);
        return NULL;
    }
    unsigned size;
    fread(&size, sizeof(unsigned), 1, f);
    //reads the size of log from file and sets value to size variable
    for (int i = 0; i < size; i++) {
        //for the size of log
        char contactname[MAX_NAME_LEN];
        unsigned int length;
        unsigned long phone;
        unsigned int zip;
        //creates variables to read parameters to 
        fread(&length, sizeof(unsigned int), 1, f);
        fread(contactname, sizeof(char), length, f);
        contactname[length] = '\0';
        //adds null terminator to end of contactname
        fread(&phone, sizeof(unsigned long), 1, f);
        fread(&zip, sizeof(unsigned int), 1, f);
        //reads in length, name, phone number and zip from binary file
        add_contact(new_contacts, contactname, phone, zip);
        // creates a new contact in contact log with those parameters
    }
    fclose(f);
    return new_contacts;
}
