# -*- coding: utf-8 -*-

import csv
import random

import requests


def rand():
    return str(random.randint(1, 255))


def generate_ips(count=1000):
    with open('ips.csv', 'a+')as f:
        writer = csv.writer(f)
        while count > 0:
            ip = rand() + '.' + rand() + '.' + rand() + '.' + rand()
            count -= 1
            writer.writerow([ip])
        print 'ip address saved ips.csv'


def add_white_ip():
    with open('ips.csv', 'r') as f:
        reader = csv.reader(f)
        for row in reader:
            ip = str.strip(row[0])
            response = send_add_ip_req(ip)
            print ip, '\t\t', response.status_code, '\t\t', response.content
    print '---finish add_white_ip---'


def send_add_ip_req(ip):
    return requests.post("http://localhost:8080/ip/white/add", data=ip)


def send_check_ip_req(ips):
    for ip in ips:
        response = requests.get("http://localhost:8080/ip/white/check", params={'ip': ip})
        print 'is ', ip, ' in white list ? ', response.content
        # break


def check_ip():
    ips = [
        '0.0.0.0',
        '1.1.1.1',
        '2.2.2.2',
        '192.168.0.1',
        '192.168.0.254',
        '192.168.1.254',
        '192.168.2.254',
        '192.168.3.254',
        '192.168.13.254',
    ]
    # with open('ips.csv', 'r') as f:
    #     for line in f:
    #         ips.append(str.strip(line))
    send_check_ip_req(ips)
    print 'finish check_ip'


if __name__ == '__main__':
    # generate_ips()
    # add_white_ip()
    send_add_ip_req('192.168.0.*')
    send_add_ip_req('192.168.3.*')
    send_add_ip_req('192.168.55.33')
    send_add_ip_req('192.168.56.33')
    send_add_ip_req('192.168.55.34')
    send_add_ip_req('192.168.13.*')

    check_ip()
