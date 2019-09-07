/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   tree_custom_order.hpp                              :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: ShnurD6 <shnurd6@mail.ru>                  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/09/07 16:15:06 by ShnurD6           #+#    #+#             */
/*   Updated: 2019/09/07 16:16:57 by ShnurD6          ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef TREE_CUSTOM_ORDER_HPP
# define TREE_CUSTOM_ORDER_HPP

#include <python3.6/Python.h>
#include <set>
#include <vector>
#include <iostream>
#include <string>
#include <sstream>

typedef int data_type;

inline void	ft_assert(bool aExpr, std::string aMessg)
{
	if (!aExpr)
	{
		std::cout << "\e[31m" << aMessg << "\e[0m" << std::endl;
		std::abort();
	}
}

#endif